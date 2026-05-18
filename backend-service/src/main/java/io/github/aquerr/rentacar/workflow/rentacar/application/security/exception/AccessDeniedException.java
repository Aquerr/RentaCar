package io.github.aquerr.rentacar.workflow.rentacar.application.security.exception;

import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.ACCESS_DENIED, status = HttpStatus.FORBIDDEN, messageKey = "error.access-denied")
public class AccessDeniedException extends RuntimeException
{

}
