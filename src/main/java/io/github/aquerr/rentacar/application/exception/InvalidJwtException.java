package io.github.aquerr.rentacar.application.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.UNAUTHORIZED, code = ApiExceptionCode.INVALID_JWT, messageKey = "jwt.invalid")
public class InvalidJwtException extends RuntimeException
{

}
