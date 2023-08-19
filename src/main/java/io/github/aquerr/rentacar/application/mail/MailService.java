package io.github.aquerr.rentacar.application.mail;

import io.github.aquerr.rentacar.application.mail.exception.CouldNotSendMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Setter
@Slf4j
public class MailService
{
    private final MailSender mailSender;
    private final MailCreator mailCreator;

    public void generateAndSend(MailMessage message) throws CouldNotSendMailException
    {
        try
        {
            log.info("Generating mail for: {}", message);
            MimeMessage mimeMessage = this.mailCreator.create(message);
            if (mimeMessage != null)
            {
                log.info("Sending mail with subject: {}, to: {}", message.getSubject(), message.getTo());
                mailSender.send(mimeMessage);
            }
        }
        catch (Exception exception)
        {
            throw new CouldNotSendMailException(exception);
        }
    }
}
