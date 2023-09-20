package io.github.aquerr.rentacar.web.rest.response;

import io.github.aquerr.rentacar.domain.reservation.dto.Reservation;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class ReservationsResponse
{
    List<Reservation> reservations;
}
