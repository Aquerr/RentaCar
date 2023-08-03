package io.github.aquerr.rentacar.domain.activation;

import io.github.aquerr.rentacar.application.config.RabbitMessageSender;
import io.github.aquerr.rentacar.application.mail.MailMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActivationLinkMailSender
{
    private final RabbitMessageSender rabbitMessageSender;

    public void send(MailMessage message)
    {
        rabbitMessageSender.send("mail.send", message);
    }
}
