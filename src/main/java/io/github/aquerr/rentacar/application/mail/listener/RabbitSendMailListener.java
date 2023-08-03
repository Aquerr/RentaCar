package io.github.aquerr.rentacar.application.mail.listener;

import io.github.aquerr.rentacar.application.mail.MailMessage;
import io.github.aquerr.rentacar.application.mail.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitSendMailListener
{
    private final MailService mailService;

    public void process(MailMessage message)
    {
        mailService.sendMail(message);
    }
}
