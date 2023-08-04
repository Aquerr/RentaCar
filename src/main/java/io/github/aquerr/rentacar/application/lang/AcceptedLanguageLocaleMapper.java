package io.github.aquerr.rentacar.application.lang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
@Slf4j
public class AcceptedLanguageLocaleMapper
{
    public List<Locale> toLocales(String acceptedLanguageHeader)
    {
        Locale locale = Locale.ENGLISH;
        try
        {
            return Locale.LanguageRange.parse(acceptedLanguageHeader)
                    .stream()
                    .map(this::toLocale)
                    .filter(Objects::nonNull)
                    .toList();
        }
        catch (Exception exception)
        {
            log.warn("Could not parse acceptedLanguageHeader = {}", acceptedLanguageHeader);
            return List.of(locale);
        }
    }

    private Locale toLocale(Locale.LanguageRange languageRange)
    {
        try
        {
            return Locale.forLanguageTag(languageRange.getRange());
        }
        catch (Exception exception)
        {
            log.warn("Could not parse locale for tag = {}", languageRange.getRange());
            return null;
        }
    }
}
