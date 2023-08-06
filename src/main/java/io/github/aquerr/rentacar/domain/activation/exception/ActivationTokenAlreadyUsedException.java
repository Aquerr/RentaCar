package io.github.aquerr.rentacar.domain.activation.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, messageKey = "activation-token.error.already-used")
public class ActivationTokenAlreadyUsedException extends RuntimeException
{
}
