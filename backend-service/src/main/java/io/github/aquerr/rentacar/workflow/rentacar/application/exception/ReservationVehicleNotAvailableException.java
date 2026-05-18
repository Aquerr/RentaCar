package io.github.aquerr.rentacar.workflow.rentacar.application.exception;

import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.RESERVATION_VEHICLE_NOT_AVAILABLE, status = HttpStatus.CONFLICT, messageKey = "reservation.error.vehicle-not-available")
public class ReservationVehicleNotAvailableException extends RuntimeException
{

}
