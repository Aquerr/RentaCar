package io.github.aquerr.rentacar.domain.pickup.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VehiclePickupLocation
{
    Long id;
    String name;
    String city;
    float latitude;
    float longtitude;
}
