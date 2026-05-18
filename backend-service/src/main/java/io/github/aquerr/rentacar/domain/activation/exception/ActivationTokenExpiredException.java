package io.github.aquerr.rentacar.domain.activation.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.ACTIVATION_TOKEN_EXPIRED, status = HttpStatus.BAD_REQUEST, messageKey = "activation-token.error.expired")
public class ActivationTokenExpiredException extends RuntimeException
{
}
