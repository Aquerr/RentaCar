package io.github.aquerr.rentacar.domain.user.password.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.PASSWORD_RESET_TOKEN_EXPIRED, status = HttpStatus.BAD_REQUEST, messageKey = "password-reset-token.error.expired")
public class PasswordResetTokenExpiredException extends RuntimeException
{
}
