package io.github.aquerr.rentacar.workflow.rentacar.domain.reservation.rest;

import io.github.aquerr.rentacar.workflow.rentacar.domain.reservation.dto.Reservation;
import lombok.Value;

@Value(staticConstructor = "of")
public class ReservationResponse
{
    Reservation reservation;
}
