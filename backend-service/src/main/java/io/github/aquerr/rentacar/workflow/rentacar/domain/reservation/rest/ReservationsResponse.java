package io.github.aquerr.rentacar.workflow.rentacar.domain.reservation.rest;

import io.github.aquerr.rentacar.workflow.rentacar.domain.reservation.dto.ProfileReservation;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class ReservationsResponse
{
    List<ProfileReservation> reservations;
}
