package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.lang.AcceptedLanguageLocaleMapper;
import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.i18n.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ResponseEntity<RestErrorResponse> handleException(HttpServletRequest httpServletRequest, RuntimeException exception) {
        log.error(exception.getMessage(), exception);

        exception = convertSpringExceptionToRentaCarIfNeeded(exception);

        if (exception instanceof AccessDeniedException)
            exception = new io.github.aquerr.rentacar.application.security.exception.AccessDeniedException();

        List<Locale> locales = acceptedLanguageLocaleMapper.toLocales(getAcceptedLanguageHeader(httpServletRequest));
        if (exception.getClass().isAnnotationPresent(ApiException.class)) {
            return convertApiExceptionToRestErrorResponse(exception, locales);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), messageService.resolveMessage("error.internal-server-error", locales)));
    }

    private RuntimeException convertSpringExceptionToRentaCarIfNeeded(RuntimeException exception)
    {
        if (exception instanceof AccessDeniedException)
            return new io.github.aquerr.rentacar.application.security.exception.AccessDeniedException();

        return exception;
    }

    private ResponseEntity<RestErrorResponse> convertApiExceptionToRestErrorResponse(RuntimeException exception, List<Locale> locales) {
        ApiException apiException = exception.getClass().getAnnotation(ApiException.class);
        return ResponseEntity.status(apiException.status())
                .body(RestErrorResponse.of(apiException.code().name(), messageService.resolveMessage(apiException.messageKey(), locales)));
    }

    private String getAcceptedLanguageHeader(HttpServletRequest httpServletRequest)
    {
        return httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
    }
}
