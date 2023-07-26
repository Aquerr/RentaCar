package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.i18n.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AllArgsConstructor
@RestControllerAdvice
@Slf4j
public class RestErrorController {

    private final MessageService messageService;

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse handleException(RuntimeException exception) {
        if (exception.getClass().isAnnotationPresent(ApiException.class)) {
            return convertApiExceptionToRestErrorResponse(exception);
        }
        return RestErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), messageService.resolveMessage("error.internal-server-error"));
    }

    private RestErrorResponse convertApiExceptionToRestErrorResponse(RuntimeException exception) {
        ApiException apiException = exception.getClass().getAnnotation(ApiException.class);
        return RestErrorResponse.of(apiException.status().value(), messageService.resolveMessage(apiException.messageKey()));
    }
}
