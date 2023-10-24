package io.github.aquerr.rentacar.application.security.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(
        status = HttpStatus.BAD_REQUEST,
        code = ApiExceptionCode.MFA_CHALLENGE_EXPIRED,
        messageKey = "user.error.mfa-challenge-expired"
)
public class MfaChallengeExpiredException extends RuntimeException
{

}
