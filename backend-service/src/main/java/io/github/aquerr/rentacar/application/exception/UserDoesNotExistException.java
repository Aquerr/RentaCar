package io.github.aquerr.rentacar.application.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.USER_NOT_EXIST, status = HttpStatus.BAD_REQUEST, messageKey = "auth.error.user-not-exist")
public class UserDoesNotExistException extends RuntimeException
{

}
