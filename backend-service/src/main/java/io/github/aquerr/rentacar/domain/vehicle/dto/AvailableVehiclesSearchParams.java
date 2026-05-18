package io.github.aquerr.rentacar.domain.vehicle.dto;

import lombok.Value;

import java.time.LocalDate;

@Value(staticConstructor = "of")
public class AvailableVehiclesSearchParams
{
    LocalDate from;
    LocalDate to;
    int page;
    int size;
}
