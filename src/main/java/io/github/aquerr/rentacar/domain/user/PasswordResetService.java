package io.github.aquerr.rentacar.domain.user;

import io.github.aquerr.rentacar.application.mail.MailMessage;
import io.github.aquerr.rentacar.application.mail.MailType;
import io.github.aquerr.rentacar.application.rabbit.RabbitMessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class PasswordResetService
{
    private final RabbitMessageSender rabbitMessageSender;

    public void resetPassword(String email)
    {
        rabbitMessageSender.send();
        MailMessage mailMessage = MailMessage.builder()
                .to(command.getEmailTo())
                .subject("Account Activation")
                .type(MailType.ACCOUNT_ACTIVATION)
                .langCode(command.getLangCode())
                .properties(Map.of("activation_token", activationTokenEntity.getToken()))
                .build()
    }
}
