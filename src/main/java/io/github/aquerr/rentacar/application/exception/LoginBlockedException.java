package io.github.aquerr.rentacar.application.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.LOGIN_BLOCKED, status = HttpStatus.FORBIDDEN, messageKey = "auth.error.login-blocked")
public class LoginBlockedException extends RuntimeException
{
}
