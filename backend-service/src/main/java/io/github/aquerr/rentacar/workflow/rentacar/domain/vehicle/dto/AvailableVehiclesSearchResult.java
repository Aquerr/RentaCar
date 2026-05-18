package io.github.aquerr.rentacar.workflow.rentacar.domain.vehicle.dto;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class AvailableVehiclesSearchResult
{
    List<VehicleBasicData> vehicles;
    long totalElements;
    int totalPages;
}
