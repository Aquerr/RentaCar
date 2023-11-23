package io.github.aquerr.rentacar.application.security.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(
        status = HttpStatus.BAD_REQUEST,
        code = ApiExceptionCode.MFA_ALREADY_ACTIVATED,
        messageKey = "user.error.mfa-already-activated"
)
public class MfaAlreadyActivatedException extends RuntimeException
{

}
