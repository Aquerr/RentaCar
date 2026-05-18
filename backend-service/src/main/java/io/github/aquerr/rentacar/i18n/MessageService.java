package io.github.aquerr.rentacar.i18n;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {

    private final MessageSource messageSource;

    public String resolveMessage(String messageKey, List<Locale> preferredLocales) {

        try
        {
            return preferredLocales.stream()
                    .map(locale -> resolveMessage(messageKey, locale))
                    .findFirst()
                    .orElse(this.resolveMessageWithDefaultLocale(messageKey));
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            return null;
        }
    }

    private String resolveMessage(String messageKey, Locale locale)
    {
        return messageSource.getMessage(messageKey, new Object[]{}, locale);
    }

    private String resolveMessageWithDefaultLocale(String messageKey)
    {
        return messageSource.getMessage(messageKey, new Object[] {}, Locale.ENGLISH);
    }
}
