package io.github.aquerr.rentacar.domain.activation.listener;

import io.github.aquerr.rentacar.application.mail.MailMessage;
import io.github.aquerr.rentacar.application.mail.MailType;
import io.github.aquerr.rentacar.domain.activation.AccountActivationService;
import io.github.aquerr.rentacar.domain.activation.ActivationLinkMailSender;
import io.github.aquerr.rentacar.domain.activation.command.AccountActivationTokenRequestCommand;
import io.github.aquerr.rentacar.domain.activation.model.ActivationToken;
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
    private final ActivationLinkMailSender activationLinkMailSender;

    @RabbitListener(queues = "account.activation.request")
    public void process(AccountActivationTokenRequestCommand command)
    {
        log.info("Processing command: {}", command);

        ActivationToken activationToken = accountActivationService.generateActivationToken(command.getCredentialsId());

        this.activationLinkMailSender.send(new MailMessage(command.getEmail(),
                "Account Activation",
                MailType.ACCOUNT_ACTIVATION,
                Map.of("activation_token", activationToken.getToken())));
    }
}
