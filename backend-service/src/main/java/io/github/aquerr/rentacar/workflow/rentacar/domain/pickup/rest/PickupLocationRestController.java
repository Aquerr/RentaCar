package io.github.aquerr.rentacar.workflow.rentacar.domain.pickup.rest;

import io.github.aquerr.rentacar.workflow.rentacar.domain.pickup.VehiclePickupLocationDictionaryService;
import io.github.aquerr.rentacar.workflow.rentacar.domain.pickup.rest.response.VehiclePickupLocationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/pickup-locations")
@AllArgsConstructor
public class PickupLocationRestController
{
    private final VehiclePickupLocationDictionaryService vehiclePickupLocationDictionaryService;

    @GetMapping
    public ResponseEntity<VehiclePickupLocationsResponse> getPickupLocations(@RequestParam(name = "lang", defaultValue = "en") Set<String> langCodes)
    {
        return ResponseEntity.ok(VehiclePickupLocationsResponse.of(this.vehiclePickupLocationDictionaryService.getVehiclePickupLocations(langCodes)));
    }
}
