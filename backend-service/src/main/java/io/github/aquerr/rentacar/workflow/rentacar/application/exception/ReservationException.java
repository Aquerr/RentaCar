package io.github.aquerr.rentacar.workflow.rentacar.application.exception;

import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.RESERVATION_NOT_FOUND, status = HttpStatus.NOT_FOUND, messageKey = "reservation.error.not-found")
public class ReservationException extends RuntimeException
{

}
