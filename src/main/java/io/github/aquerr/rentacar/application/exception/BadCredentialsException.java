package io.github.aquerr.rentacar.application.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, messageKey = "auth.error.bad-credentials")
public class BadCredentialsException extends RuntimeException {

}
