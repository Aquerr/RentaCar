package io.github.aquerr.rentacar.application.security.mfa;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.RentaCarUserDetailsService;
import io.github.aquerr.rentacar.application.security.dto.AuthResult;
import io.github.aquerr.rentacar.application.security.dto.MfaActivationResult;
import io.github.aquerr.rentacar.application.security.dto.MfaAuthResult;
import io.github.aquerr.rentacar.application.security.exception.AccessDeniedException;
import io.github.aquerr.rentacar.application.security.exception.BadMfaAuthenticationException;
import io.github.aquerr.rentacar.application.security.exception.MfaBadAuthCodeException;
import io.github.aquerr.rentacar.application.security.exception.MfaChallengeExpiredException;
import io.github.aquerr.rentacar.application.security.mfa.converter.UserMfaSettingsConverter;
import io.github.aquerr.rentacar.application.security.mfa.dto.UserMfaSettings;
import io.github.aquerr.rentacar.application.security.mfa.model.MfaAuthChallengeEntity;
import io.github.aquerr.rentacar.application.security.mfa.model.MfaRecoveryCodeEntity;
import io.github.aquerr.rentacar.application.security.mfa.model.UserMfaSettingsEntity;
import io.github.aquerr.rentacar.application.security.mfa.model.UserMfaTotpEntity;
import io.github.aquerr.rentacar.repository.mfa.MfaAuthChallengeRepository;
import io.github.aquerr.rentacar.repository.mfa.MfaRecoveryCodesRepository;
import io.github.aquerr.rentacar.repository.mfa.UserMfaSettingsRepository;
import io.github.aquerr.rentacar.repository.mfa.UserMfaTotpEntityRepository;
import io.github.aquerr.rentacar.web.rest.request.MfaAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MfaAuthenticationService
{
    private final MfaCodeGenerator mfaCodeGenerator;
    private final MfaCodeVerifier mfaCodeVerifier;
    private final UserMfaSettingsConverter mfaSettingsConverter;
    private final RentaCarUserDetailsService rentaCarUserDetailsService;
    private final UserMfaTotpEntityRepository mfaTotpEntityRepository;
    private final UserMfaSettingsRepository mfaSettingsRepository;
    private final MfaRecoveryCodesRepository mfaRecoveryCodesRepository;
    private final MfaAuthChallengeRepository mfaAuthChallengeRepository;

    public MfaAuthResult auth(long credentialsId, String code)
    {
        UserMfaSettingsEntity userMfaSettingsEntity = mfaSettingsRepository.findByCredentialsId(credentialsId)
                .orElse(null);
        if (userMfaSettingsEntity == null || !userMfaSettingsEntity.isVerified())
        {
            throw new AccessDeniedException();
        }

        if (userMfaSettingsEntity.getMfaType() == MfaType.TOTP)
        {
            return authTotp(userMfaSettingsEntity, code);
        }
        return MfaAuthResult.of(false);
    }

    private MfaAuthResult authTotp(UserMfaSettingsEntity userMfaSettingsEntity, String code)
    {
        UserMfaTotpEntity userMfaTotpEntity = mfaTotpEntityRepository.findByCredentialsId(userMfaSettingsEntity.getCredentialsId())
                .orElseThrow();

        String secret = userMfaTotpEntity.getSecret();

        //TODO: Add handling for recovery codes...
        return MfaAuthResult.of(this.mfaCodeVerifier.verify(secret, code));
    }

    @Transactional
    public MfaActivationResult activate(long credentialsId, String code)
    {
        UserMfaSettingsEntity userMfaSettingsEntity = mfaSettingsRepository.findByCredentialsId(credentialsId)
                .orElse(null);
        if (userMfaSettingsEntity == null || userMfaSettingsEntity.isVerified())
        {
            throw new AccessDeniedException();
        }

        String secret = null;
        if (userMfaSettingsEntity.getMfaType() == MfaType.TOTP)
        {
            secret = this.mfaTotpEntityRepository.findByCredentialsId(credentialsId)
                    .orElseThrow(() -> new IllegalStateException("Missing MFA settings!"))
                    .getSecret();
        }

        if (secret == null)
        {
            throw new IllegalStateException("Missing MFA settings!");
        }

        if (!this.mfaCodeVerifier.verify(secret, code))
        {
            throw new AccessDeniedException();
        }

        userMfaSettingsEntity.setVerified(true);
        mfaSettingsRepository.save(userMfaSettingsEntity);

        Set<String> recoveryCodes = this.mfaCodeGenerator.generateRecoveryCodes();

        mfaRecoveryCodesRepository.saveAll(recoveryCodes.stream()
                .map(recoveryCode -> new MfaRecoveryCodeEntity(null, credentialsId, recoveryCode))
                .collect(Collectors.toList()));

        return MfaActivationResult.of(recoveryCodes);
    }

    @Transactional
    public void deleteMfaForUser(long credentialsId)
    {
        UserMfaSettingsEntity entity = this.mfaSettingsRepository.findByCredentialsId(credentialsId).orElseThrow();
        if (entity.getMfaType() == MfaType.TOTP)
        {
            this.mfaTotpEntityRepository.deleteByCredentialsId(credentialsId);
        }

        this.mfaSettingsRepository.deleteByCredentialsId(credentialsId);
    }

    @Transactional
    public String generateQRCode(AuthenticatedUser authenticatedUser)
    {
        String userIdentifier = authenticatedUser.getUsername();
        String secret = this.mfaCodeGenerator.generateSecret();
        String qrCodeDataUri = this.mfaCodeGenerator.generateQrCodeDataUri(secret, userIdentifier);

        //TODO: Save secret in DB... and set MFA as not verified/linked.

        saveUserTotp(authenticatedUser, secret);

        return qrCodeDataUri;
    }

    public List<String> getAvailableAuthTypes()
    {
        return Arrays.stream(MfaType.values()).map(Enum::name).toList();
    }

    private void saveUserTotp(AuthenticatedUser authenticatedUser, String secret)
    {
        Optional<UserMfaSettings> userMfaSettingsSaved = getUserMfaSettings(authenticatedUser.getId());
        UserMfaSettingsEntity userMfaSettingsEntity = new UserMfaSettingsEntity();
        userMfaSettingsSaved.ifPresent(userMfaSettings -> userMfaSettingsEntity.setId(userMfaSettings.getId()));
        userMfaSettingsEntity.setCredentialsId(authenticatedUser.getId());
        userMfaSettingsEntity.setVerified(false);
        userMfaSettingsEntity.setMfaType(MfaType.TOTP);
        this.mfaSettingsRepository.save(userMfaSettingsEntity);

        Optional<UserMfaTotpEntity> userMfaTotpEntitySaved = this.mfaTotpEntityRepository.findByCredentialsId(authenticatedUser.getId());
        UserMfaTotpEntity userMfaTotpEntity = new UserMfaTotpEntity();
        userMfaTotpEntitySaved.ifPresent(userMfaToTp -> userMfaTotpEntity.setId(userMfaToTp.getId()));
        userMfaTotpEntity.setSecret(secret);
        userMfaTotpEntity.setCredentialsId(authenticatedUser.getId());
        this.mfaTotpEntityRepository.save(userMfaTotpEntity);
    }

    public Optional<UserMfaSettings> getUserMfaSettings(Long credentialsId)
    {
        return mfaSettingsRepository.findByCredentialsId(credentialsId)
                .map(this.mfaSettingsConverter::convertToDto);
    }

    @Transactional
    public String generateMfaChallenge(AuthenticatedUser authenticatedUser)
    {
        String challenge = this.mfaCodeGenerator.generateChallenge();
        MfaAuthChallengeEntity mfaAuthChallengeEntity = new MfaAuthChallengeEntity();
        mfaAuthChallengeEntity.setCredentialsId(authenticatedUser.getId());
        mfaAuthChallengeEntity.setChallenge(challenge);
        mfaAuthChallengeEntity.setExpirationDateTime(ZonedDateTime.now().plusMinutes(1));
        mfaAuthChallengeRepository.save(mfaAuthChallengeEntity);

        return challenge;
    }

    private MfaAuthChallengeEntity getChallenge(String challenge)
    {
        return this.mfaAuthChallengeRepository.findById(challenge)
                .orElse(null);
    }

    @Transactional
    public AuthResult authenticate(MfaAuthRequest mfaAuthRequest)
    {
        MfaAuthChallengeEntity challengeEntity = getChallenge(mfaAuthRequest.getChallenge());
        if (challengeEntity == null)
            throw new BadMfaAuthenticationException();
        if (challengeEntity.getExpirationDateTime().isBefore(ZonedDateTime.now()))
            throw new MfaChallengeExpiredException();

        AuthenticatedUser authenticatedUser = rentaCarUserDetailsService.loadById(challengeEntity.getCredentialsId());
        UserMfaTotpEntity mfaTotpEntity = mfaTotpEntityRepository.findByCredentialsId(challengeEntity.getCredentialsId()).orElse(null);
        if (mfaTotpEntity == null)
            throw new BadMfaAuthenticationException();

        String secret = mfaTotpEntity.getSecret();

        //TODO: Handle recovery codes...
        //TODO: We should firstly check if auth code is recovery code.

        String authCode = mfaAuthRequest.getCode();
        if (!mfaCodeVerifier.verify(secret, authCode))
            throw new MfaBadAuthCodeException();

        return AuthResult.authenticated(authenticatedUser);
    }
}
