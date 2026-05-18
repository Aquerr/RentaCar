package io.github.aquerr.rentacar.workflow.rentacar.application.exception;

import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.BAD_CREDENTIALS, status = HttpStatus.BAD_REQUEST, messageKey = "auth.error.bad-credentials")
public class BadCredentialsException extends RuntimeException {

}
