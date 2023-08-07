package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.vehicle.VehicleService;
import io.github.aquerr.rentacar.web.rest.response.VehicleBasicDataResponse;
import io.github.aquerr.rentacar.web.rest.response.VehicleFullDataResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicles")
@AllArgsConstructor
public class VehicleRestController {
    private final VehicleService vehicleService;

    @GetMapping("/full-data/{vehicleId}")
    public ResponseEntity<VehicleFullDataResponse> getVehicleFullData(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(VehicleFullDataResponse.of(this.vehicleService.getVehicleFullData(vehicleId)));
    }

    @GetMapping("/available")
    public ResponseEntity<VehicleBasicDataResponse> getVehiclesAvailable(@RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo) {
        return ResponseEntity.ok(VehicleBasicDataResponse.of(this.vehicleService.getVehiclesAvailable(dateFrom, dateTo)));
    }
}
