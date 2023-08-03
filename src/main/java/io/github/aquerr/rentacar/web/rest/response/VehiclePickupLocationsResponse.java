package io.github.aquerr.rentacar.web.rest.response;

import io.github.aquerr.rentacar.domain.pickup.dto.VehiclePickupLocation;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class VehiclePickupLocationsResponse
{
    List<VehiclePickupLocation> locations;
}
