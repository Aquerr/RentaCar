package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.vehicle.VehicleService;
import io.github.aquerr.rentacar.domain.vehicle.dto.AvailableVehiclesSearchParams;
import io.github.aquerr.rentacar.web.rest.response.SearchVehicleBasicDataResponse;
import io.github.aquerr.rentacar.web.rest.response.VehicleAvailabilityResponse;
import io.github.aquerr.rentacar.web.rest.response.VehicleFullDataResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
    public ResponseEntity<SearchVehicleBasicDataResponse> getVehiclesAvailable(@RequestParam("dateFrom") LocalDate dateFrom,
                                                                               @RequestParam("dateTo") LocalDate dateTo,
                                                                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(SearchVehicleBasicDataResponse.of(this.vehicleService.getVehiclesAvailable(AvailableVehiclesSearchParams.of(dateFrom, dateTo, page, size))));
    }

    @GetMapping("/{vehicleId}/available")
    public ResponseEntity<VehicleAvailabilityResponse> isVehicleAvailable(@RequestParam("dateFrom") LocalDate dateFrom,
                                                                          @RequestParam("dateTo") LocalDate dateTo,
                                                                          @PathVariable("vehicleId") int vehicleId)
    {
        return ResponseEntity.ok(VehicleAvailabilityResponse.of(this.vehicleService.isVehicleAvailable(vehicleId, dateFrom, dateTo)));
    }
}
