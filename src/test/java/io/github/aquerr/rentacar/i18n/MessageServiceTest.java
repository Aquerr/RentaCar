package io.github.aquerr.rentacar.i18n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest
{
    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageService messageService;

    @Test
    void shouldResolveMessageForGivenMessageKeyForFirstPreferredLocate()
    {
        // given
        String messageKey = "test";
        List<Locale> preferredLocales = List.of(Locale.forLanguageTag("pl"), Locale.ENGLISH);
        given(messageSource.getMessage(messageKey, new Object[]{}, Locale.forLanguageTag("pl")))
                .willReturn("moja-wiadomosc");

        // when
        String message = messageService.resolveMessage(messageKey, preferredLocales);

        // then
        assertThat(message).isEqualTo("moja-wiadomosc");
    }

    @Test
    void shouldResolveMessageUseDefaultLocaleWhenNoPreferredLocales()
    {
        // given
        String messageKey = "test";
        List<Locale> preferredLocales = List.of();
        given(messageSource.getMessage(messageKey, new Object[]{}, Locale.ENGLISH))
                .willReturn("message");

        // when
        String message = messageService.resolveMessage(messageKey, preferredLocales);

        // then
        assertThat(message).isEqualTo("message");
    }

    @Test
    void shouldResolveMessageReturnNullWhenMessageNotFound()
    {
        // given
        String messageKey = "test";
        List<Locale> preferredLocales = List.of();
        given(messageSource.getMessage(messageKey, new Object[]{}, Locale.ENGLISH))
                .willThrow(NoSuchMessageException.class);

        // when
        String message = messageService.resolveMessage(messageKey, preferredLocales);

        // then
        assertThat(message).isNull();
    }
}