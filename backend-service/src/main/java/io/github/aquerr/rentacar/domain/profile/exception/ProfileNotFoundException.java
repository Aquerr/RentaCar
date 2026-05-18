package io.github.aquerr.rentacar.domain.profile.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.PROFILE_NOT_FOUND, status = HttpStatus.NOT_FOUND, messageKey = "profile.error.not-found")
public class ProfileNotFoundException extends RuntimeException
{

}
