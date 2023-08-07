package io.github.aquerr.rentacar.web.rest.response;

import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleFullData;
import lombok.Value;

@Value(staticConstructor = "of")
public class VehicleFullDataResponse
{
    VehicleFullData vehicle;
}
