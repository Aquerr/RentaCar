package io.github.aquerr.rentacar.domain.user.password;

import io.github.aquerr.rentacar.application.security.challengetoken.ChallengeTokenService;
import io.github.aquerr.rentacar.application.security.challengetoken.dto.ChallengeToken;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService
{
    private final ChallengeTokenService challengeTokenService;

    @Value("${rentacar.security.account.password-reset-token.expiration-time}")
    private Duration passwordResetTokenExpirationTime;

    public Optional<ChallengeToken> getResetToken(String token)
    {
        return Optional.ofNullable(this.challengeTokenService.find(token, OperationType.PASSWORD_RESET));
    }

    @Transactional
    public ChallengeToken invalidateOldActivationTokensAndGenerateNew(long userId)
    {
        this.challengeTokenService.invalidateOldChallengeTokens(userId, OperationType.PASSWORD_RESET);
        return this.challengeTokenService.generateAndSave(userId, OperationType.PASSWORD_RESET, passwordResetTokenExpirationTime);
    }

    @Transactional
    public void markAsUsed(String token)
    {
        this.challengeTokenService.markAsUsed(token, OperationType.PASSWORD_RESET);
    }
}
