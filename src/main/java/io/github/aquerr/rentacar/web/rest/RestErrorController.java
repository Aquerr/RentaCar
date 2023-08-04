package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.lang.AcceptedLanguageLocaleMapper;
import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.i18n.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestControllerAdvice
@Slf4j
public class RestErrorController {

    private final AcceptedLanguageLocaleMapper acceptedLanguageLocaleMapper;
    private final MessageService messageService;

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse handleException(HttpServletRequest httpServletRequest, RuntimeException exception) {
        log.error(exception.getMessage(), exception);

        List<Locale> locales = acceptedLanguageLocaleMapper.toLocales(getAcceptedLanguageHeader(httpServletRequest));
        if (exception.getClass().isAnnotationPresent(ApiException.class)) {
            return convertApiExceptionToRestErrorResponse(exception, locales);
        }
        return RestErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), messageService.resolveMessage("error.internal-server-error", locales));
    }

    private RestErrorResponse convertApiExceptionToRestErrorResponse(RuntimeException exception, List<Locale> locales) {
        ApiException apiException = exception.getClass().getAnnotation(ApiException.class);
        return RestErrorResponse.of(apiException.status().value(), messageService.resolveMessage(apiException.messageKey(), locales));
    }

    private String getAcceptedLanguageHeader(HttpServletRequest httpServletRequest)
    {
        return httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
    }
}
