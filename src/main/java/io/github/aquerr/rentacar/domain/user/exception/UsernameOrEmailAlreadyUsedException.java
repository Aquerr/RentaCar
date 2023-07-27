package io.github.aquerr.rentacar.domain.user.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.CONFLICT, messageKey = "user.error.username-email-already-used")
public class UsernameOrEmailAlreadyUsedException extends RuntimeException
{

}
