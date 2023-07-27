package io.github.aquerr.rentacar.application.security.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.UNAUTHORIZED, messageKey = "error.access-denied")
public class AccessDeniedException extends RuntimeException
{

}
