package io.github.aquerr.rentacar.application.lang;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Locale;

@Component
@AllArgsConstructor
public class RequestLocaleExtractor
{
    private final AcceptedLanguageLocaleMapper acceptedLanguageLocaleMapper;

    public List<Locale> getPreferredLocales()
    {
        return acceptedLanguageLocaleMapper.toLocales(getAcceptedLanguageHeaderFromCurrentHttpRequest());
    }

    public List<LangCode> getPreferredLangCodes()
    {
        return getPreferredLocales().stream()
                .map(Locale::getLanguage)
                .map(LangCode::forCode)
                .toList();
    }

    public LangCode getPreferredLangCode()
    {
        return getPreferredLangCodes().stream()
                .findFirst()
                .orElse(LangCode.ENGLISH);
    }

    private String getAcceptedLanguageHeaderFromCurrentHttpRequest()
    {
        try
        {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        }
        catch (IllegalStateException exception)
        {
            return LangCode.ENGLISH.getCode();
        }
    }
}
