package io.github.aquerr.rentacar.domain.user.password;

import io.github.aquerr.rentacar.application.security.challengetoken.ChallengeTokenGenerator;
import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import io.github.aquerr.rentacar.domain.user.password.converter.PasswordResetTokenConverter;
import io.github.aquerr.rentacar.domain.user.password.dto.PasswordResetTokenDto;
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
    private final ChallengeTokenGenerator challengeTokenGenerator;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordResetTokenConverter passwordResetTokenConverter;

    @Value("${rentacar.security.account.password-reset-token.expiration-time}")
    private Duration passwordResetTokenExpirationTime;

    public Optional<PasswordResetTokenDto> getResetToken(String token)
    {
        return this.passwordResetTokenRepository.findByToken(token).map(this.passwordResetTokenConverter::toDto);
    }

    @Transactional
    public ChallengeTokenEntity invalidateOldActivationTokensAndGenerateNew(long userId)
    {
        this.passwordResetTokenRepository.invalidateOldResetTokens(userId);

        ChallengeTokenEntity challengeTokenEntity = ChallengeTokenEntity.builder()
                        .userId(userId)
                        .expirationDate(ZonedDateTime.now().plus(passwordResetTokenExpirationTime))
                        .token(this.challengeTokenGenerator.generate())
                        .used(false)
                        .build();

        this.challengeTokenGenerator.save(challengeTokenEntity);

        return challengeTokenEntity;
    }

    @Transactional
    public void markAsUsed(String token)
    {
        this.passwordResetTokenRepository.updateByTokenSetUsedTrue(token);
    }
}
