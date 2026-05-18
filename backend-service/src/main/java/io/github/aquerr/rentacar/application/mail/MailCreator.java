package io.github.aquerr.rentacar.application.mail;

import io.github.aquerr.rentacar.application.mail.exception.CouldNotGenerateMailException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

public interface MailCreator
{
    MimeMessage create(MailMessageProperties message) throws CouldNotGenerateMailException;

    /**
     * Used only in development/test environment.
     */
    @Deprecated
    @Slf4j
    class NoOpMailCreator implements MailCreator
    {
        @Override
        public MimeMessage create(MailMessageProperties message) throws CouldNotGenerateMailException
        {
            log.info("Got mail message {}, returning null", message);
            return null;
        }
    }
}
