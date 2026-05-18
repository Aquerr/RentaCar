package io.github.aquerr.rentacar.workflow.rentacar.application.security.exception;

import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(
        status = HttpStatus.BAD_REQUEST,
        code = ApiExceptionCode.BAD_CREDENTIALS,
        messageKey = "user.error.bad-mfa-authentication")
public class BadMfaAuthenticationException extends RuntimeException
{

}
