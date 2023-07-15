package io.github.aquerr.rentacar.i18n;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String resolveMessage(String messageKey) {
        return messageSource.getMessage(messageKey, new Object[] {}, Locale.US);
    }
}
