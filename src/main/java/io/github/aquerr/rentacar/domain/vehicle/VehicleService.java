package io.github.aquerr.rentacar.domain.vehicle;

import io.github.aquerr.rentacar.domain.vehicle.converter.VehicleConverter;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleBasicData;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleFullData;
import io.github.aquerr.rentacar.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleService
{
    private final VehicleRepository vehicleRepository;
    private final VehicleConverter vehicleConverter;

    @Transactional(readOnly = true)
    public VehicleFullData getVehicleFullData(Integer vehicleId)
    {
        return vehicleRepository.findById(vehicleId)
                .map(this.vehicleConverter::toFullData)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<VehicleBasicData> getVehiclesAvailable(String dateFrom, String dateTo)
    {
        // TODO logika pobierania pojazdów nie zarezerwowanych
        return vehicleRepository.findAll().stream()
                .map(this.vehicleConverter::toBasicData)
                .collect(Collectors.toList());
    }
}
