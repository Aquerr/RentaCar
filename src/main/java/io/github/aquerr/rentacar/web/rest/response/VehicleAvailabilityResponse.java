package io.github.aquerr.rentacar.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value(staticConstructor = "of")
public class VehicleAvailabilityResponse
{
    @JsonProperty("available")
    boolean available;
}
