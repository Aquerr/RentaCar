package io.github.aquerr.rentacar.workflow.rentacar.application.exception;

import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.USER_LOCKED, status = HttpStatus.FORBIDDEN, messageKey = "auth.error.user-locked")
public class UserLockedException extends RuntimeException
{

}
