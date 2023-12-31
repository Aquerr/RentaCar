package io.github.aquerr.rentacar.domain.activation.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.ACTIVATION_TOKEN_USED, status = HttpStatus.BAD_REQUEST, messageKey = "activation-token.error.already-used")
public class ActivationTokenAlreadyUsedException extends RuntimeException
{
}
