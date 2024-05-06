package io.github.aquerr.rentacar.application.mail.publisher;

import io.github.aquerr.rentacar.application.mail.MailMessageProperties;
import io.github.aquerr.rentacar.application.rabbit.RabbitMessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitSendMailPublisher
{
    private final RabbitMessageSender rabbitMessageSender;

    public void publish(MailMessageProperties mailMessageProperties)
    {
        try
        {
            rabbitMessageSender.send("mail.send.request", mailMessageProperties);
        }
        catch (Exception e)
        {
            //TODO: We should retry...
            log.error("Could not send message {}", mailMessageProperties, e);
        }
    }
}
