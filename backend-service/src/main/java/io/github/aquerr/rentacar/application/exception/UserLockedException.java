package io.github.aquerr.rentacar.application.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.USER_LOCKED, status = HttpStatus.FORBIDDEN, messageKey = "auth.error.user-locked")
public class UserLockedException extends RuntimeException
{

}
