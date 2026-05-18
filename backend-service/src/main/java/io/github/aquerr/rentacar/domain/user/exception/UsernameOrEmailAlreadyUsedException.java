package io.github.aquerr.rentacar.domain.user.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.USER_EXISTS, status = HttpStatus.CONFLICT, messageKey = "user.error.username-email-already-used")
public class UsernameOrEmailAlreadyUsedException extends RuntimeException
{

}
