package io.github.aquerr.rentacar.i18n;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String resolveMessage(String messageKey, List<Locale> preferredLocales) {

        return preferredLocales.stream()
                .map(locale -> messageSource.getMessage(messageKey, new Object[]{}, locale))
                .findFirst()
                .orElse(this.resolveMessage(messageKey));
    }

    private String resolveMessage(String messageKey)
    {
        return messageSource.getMessage(messageKey, new Object[] {}, Locale.ENGLISH);
    }
}
