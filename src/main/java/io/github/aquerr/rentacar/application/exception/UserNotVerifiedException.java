package io.github.aquerr.rentacar.application.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.FORBIDDEN, messageKey = "auth.error.user-not-verified")
public class UserNotVerifiedException extends RuntimeException
{

}
