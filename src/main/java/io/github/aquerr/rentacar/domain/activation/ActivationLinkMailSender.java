package io.github.aquerr.rentacar.domain.activation;

import io.github.aquerr.rentacar.application.config.RabbitMessageSender;
import io.github.aquerr.rentacar.application.exception.MessageSendException;
import io.github.aquerr.rentacar.application.mail.MailMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ActivationLinkMailSender
{
    private final RabbitMessageSender rabbitMessageSender;

    public void send(MailMessage message)
    {
        try
        {
            rabbitMessageSender.send("mail.send", message);
        }
        catch (MessageSendException e)
        {
            //TODO: We should retry...
            log.error("Could not send message {}", message, e);
        }
    }
}
