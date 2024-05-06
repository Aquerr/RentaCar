package io.github.aquerr.rentacar.domain.user.password;

import io.github.aquerr.rentacar.application.security.AccessTokenGenerator;
import io.github.aquerr.rentacar.domain.user.password.converter.PasswordResetTokenConverter;
import io.github.aquerr.rentacar.domain.user.password.dto.PasswordResetTokenDto;
import io.github.aquerr.rentacar.domain.user.password.model.PasswordResetTokenEntity;
import io.github.aquerr.rentacar.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService
{
    private final AccessTokenGenerator accessTokenGenerator;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordResetTokenConverter passwordResetTokenConverter;

    @Value("${rentacar.security.account.password-reset-token.expiration-time}")
    private Duration passwordResetTokenExpirationTime;

    public Optional<PasswordResetTokenDto> getResetToken(String token)
    {
        return this.passwordResetTokenRepository.findByToken(token).map(this.passwordResetTokenConverter::toDto);
    }

    @Transactional
    public PasswordResetTokenEntity invalidateOldActivationTokensAndGenerateNew(long credentialsId)
    {
        this.passwordResetTokenRepository.invalidateOldResetTokens(credentialsId);

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setCredentialsId(credentialsId);
        passwordResetTokenEntity.setExpirationDate(ZonedDateTime.now().plus(passwordResetTokenExpirationTime));
        passwordResetTokenEntity.setToken(this.accessTokenGenerator.generate());
        passwordResetTokenEntity.setUsed(false);
        this.passwordResetTokenRepository.save(passwordResetTokenEntity);

        return passwordResetTokenEntity;
    }

    @Transactional
    public void markAsUsed(String token)
    {
        this.passwordResetTokenRepository.updateByTokenSetUsedTrue(token);
    }
}
