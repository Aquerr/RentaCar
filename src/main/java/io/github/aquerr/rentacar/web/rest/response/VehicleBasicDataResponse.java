package io.github.aquerr.rentacar.web.rest.response;

import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleBasicData;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class VehicleBasicDataResponse
{
    List<VehicleBasicData> vehicles;
}
