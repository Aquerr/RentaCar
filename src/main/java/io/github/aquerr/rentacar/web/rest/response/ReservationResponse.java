package io.github.aquerr.rentacar.web.rest.response;

import io.github.aquerr.rentacar.domain.reservation.dto.Reservation;
import lombok.Value;

@Value(staticConstructor = "of")
public class ReservationResponse
{
    Reservation reservation;
}
