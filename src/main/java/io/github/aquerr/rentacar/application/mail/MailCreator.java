package io.github.aquerr.rentacar.application.mail;

import io.github.aquerr.rentacar.application.mail.exception.CouldNotGenerateMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

public interface MailCreator
{
    MimeMessage create(MailMessage message) throws CouldNotGenerateMailException;

    interface MailMessageCreator
    {
        MimeMessage build(MailMessage message) throws MessagingException, IOException;
    }

    /**
     * Used only in development/test environment.
     */
    @Deprecated
    @Slf4j
    class NoOpMailCreator implements MailCreator
    {
        @Override
        public MimeMessage create(MailMessage message) throws CouldNotGenerateMailException
        {
            log.info("Got mail message {}, returning null", message);
            return null;
        }
    }
}
