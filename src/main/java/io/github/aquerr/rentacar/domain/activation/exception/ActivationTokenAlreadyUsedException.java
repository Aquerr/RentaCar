package io.github.aquerr.rentacar.domain.activation.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, messageKey = "")
public class ActivationTokenAlreadyUsedException extends RuntimeException
{
}
