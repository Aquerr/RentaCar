package io.github.aquerr.rentacar.application.security.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.ACCESS_DENIED, status = HttpStatus.UNAUTHORIZED, messageKey = "error.access-denied")
public class AccessDeniedException extends RuntimeException
{

}
