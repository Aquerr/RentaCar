package io.github.aquerr.rentacar.domain.activation;

import io.github.aquerr.rentacar.application.config.RabbitMessageSender;
import io.github.aquerr.rentacar.domain.activation.command.AccountActivationTokenRequestCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountActivationTokenRequester
{
    private final RabbitMessageSender rabbitMessageSender;

    public void requestActivationToken(Long credentialsId, String email)
    {
        rabbitMessageSender.send("account.activation.request", new AccountActivationTokenRequestCommand(credentialsId, email));
    }
}
