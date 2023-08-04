package io.github.aquerr.rentacar.domain.activation;

import io.github.aquerr.rentacar.application.config.RabbitMessageSender;
import io.github.aquerr.rentacar.application.exception.MessageSendException;
import io.github.aquerr.rentacar.application.lang.LangCode;
import io.github.aquerr.rentacar.domain.activation.command.AccountActivationTokenRequestCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AccountActivationTokenRequester
{
    private final RabbitMessageSender rabbitMessageSender;

    public void requestActivationToken(Long credentialsId, String email, LangCode langCode)
    {
        try
        {
            rabbitMessageSender.send("account.activation.request", new AccountActivationTokenRequestCommand(credentialsId, email, langCode));
        }
        catch (MessageSendException e)
        {
            //TODO: We should retry...
            log.error(String.format("Could not send message: credentialsId: %s, email: %s", credentialsId, email), e);
        }
    }
}
