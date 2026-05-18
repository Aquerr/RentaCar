package io.github.aquerr.rentacar.application.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.USER_NOT_ACTIVATED, status = HttpStatus.FORBIDDEN, messageKey = "auth.error.user-not-activated")
public class UserNotActivatedException extends RuntimeException
{

}
