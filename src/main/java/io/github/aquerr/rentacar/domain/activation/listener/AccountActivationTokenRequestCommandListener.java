package io.github.aquerr.rentacar.domain.activation.listener;

import io.github.aquerr.rentacar.application.mail.MailMessage;
import io.github.aquerr.rentacar.application.mail.MailType;
import io.github.aquerr.rentacar.application.rabbit.RabbitMessageSender;
import io.github.aquerr.rentacar.domain.activation.AccountActivationService;
import io.github.aquerr.rentacar.domain.activation.command.AccountActivationTokenRequestCommand;
import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountActivationTokenRequestCommandListener
{
    private final AccountActivationService accountActivationService;
    private final RabbitMessageSender rabbitMessageSender;

    @RabbitListener(queues = "account.activation.request")
    public void process(AccountActivationTokenRequestCommand command)
    {
        log.info("Processing command: {}", command);

        ActivationTokenEntity activationTokenEntity = accountActivationService.invalidateOldActivationTokensAndGenerateNew(command.getCredentialsId());

        MailMessage mailMessage = MailMessage.builder()
                        .to(command.getEmailTo())
                        .subject("Account Activation")
                        .type(MailType.ACCOUNT_ACTIVATION)
                        .langCode(command.getLangCode())
                        .properties(Map.of("activation_token", activationTokenEntity.getToken()))
                .build();
        sendMail(mailMessage);
    }

    private void sendMail(MailMessage mailMessage)
    {
        try
        {
            rabbitMessageSender.send("mail.send", mailMessage);
        }
        catch (Exception e)
        {
            //TODO: We should retry...
            log.error("Could not send message {}", mailMessage, e);
        }
    }
}
