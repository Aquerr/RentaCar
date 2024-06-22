package io.github.aquerr.rentacar.domain.user.password;

import io.github.aquerr.rentacar.application.lang.RequestLocaleExtractor;
import io.github.aquerr.rentacar.application.mail.MailMessageProperties;
import io.github.aquerr.rentacar.application.mail.MailType;
import io.github.aquerr.rentacar.application.mail.placeholder.CommonPlaceholders;
import io.github.aquerr.rentacar.application.mail.publisher.RabbitSendMailPublisher;
import io.github.aquerr.rentacar.application.security.challengetoken.dto.ChallengeToken;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.domain.user.dto.UserCredentials;
import io.github.aquerr.rentacar.domain.user.password.exception.PasswordResetTokenAlreadyUsedException;
import io.github.aquerr.rentacar.domain.user.password.exception.PasswordResetTokenExpiredException;
import io.github.aquerr.rentacar.domain.user.password.exception.PasswordResetTokenNotFoundException;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class PasswordResetService
{
    private final RabbitSendMailPublisher rabbitSendMailPublisher;
    private final RequestLocaleExtractor requestLocaleExtractor;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserService userService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public void initPasswordReset(String email)
    {
        UserCredentials userCredentials = this.userService.findByEmail(email);
        if (userCredentials == null)
        {
            log.info("No user found with email {}", email);
            return;
        }

        String token = this.passwordResetTokenService.invalidateOldActivationTokensAndGenerateNew(userCredentials.getId()).token();

        MailMessageProperties mailMessageProperties = MailMessageProperties.builder()
                .to(email)
                .type(MailType.PASSWORD_RESET)
                .langCode(requestLocaleExtractor.getPreferredLangCode())
                .additionalProperties(Map.of(CommonPlaceholders.TOKEN, token))
                .build();

        rabbitSendMailPublisher.publish(mailMessageProperties);
    }

    @Transactional
    public void changePassword(String token, String newPassword)
    {
        ChallengeToken passwordResetTokenDto = this.passwordResetTokenService.getResetToken(token)
                .orElseThrow(PasswordResetTokenNotFoundException::new);
        if (passwordResetTokenDto.used())
            throw new PasswordResetTokenAlreadyUsedException();
        if (passwordResetTokenDto.expirationDate().isBefore(OffsetDateTime.now()))
            throw new PasswordResetTokenExpiredException();

        this.userCredentialsRepository.updateByUserIdSetPassword(passwordResetTokenDto.userId(), passwordEncoder.encode(newPassword));
        this.passwordResetTokenService.markAsUsed(token);
    }
}
