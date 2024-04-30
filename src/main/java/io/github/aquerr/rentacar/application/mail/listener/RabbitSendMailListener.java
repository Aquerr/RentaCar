package io.github.aquerr.rentacar.application.mail.listener;

import io.github.aquerr.rentacar.application.mail.MailMessageProperties;
import io.github.aquerr.rentacar.application.mail.MailService;
import io.github.aquerr.rentacar.application.mail.exception.CouldNotSendMailException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RabbitSendMailListener
{
    private final MailService mailService;

    public void process(MailMessageProperties message)
    {
        try
        {
            mailService.generateAndSend(message);
        }
        catch (CouldNotSendMailException e)
        {
            //TODO: We should retry few times before giving up.
            log.error(e.getMessage(), e);
        }
    }
}
