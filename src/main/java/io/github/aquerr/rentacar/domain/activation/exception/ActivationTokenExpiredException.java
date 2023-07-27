package io.github.aquerr.rentacar.domain.activation.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, messageKey = "activation-token.error.expired")
public class ActivationTokenExpiredException extends RuntimeException
{
}
