package io.github.aquerr.rentacar.domain.pickup;

import io.github.aquerr.rentacar.domain.pickup.converter.VehiclePickuLocationConverter;
import io.github.aquerr.rentacar.domain.pickup.dto.VehiclePickupLocation;
import io.github.aquerr.rentacar.repository.VehiclePickupLocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class VehiclePickupLocationDictionaryService
{
    private final VehiclePickupLocationRepository vehiclePickupLocationRepository;
    private final VehiclePickuLocationConverter vehiclePickuLocationConverter;

    @Transactional(readOnly = true)
    public List<VehiclePickupLocation> getVehiclePickupLocations(Set<String> langCodes)
    {
        return vehiclePickupLocationRepository.findAllByLangCodeIn(langCodes).stream()
                .map(this.vehiclePickuLocationConverter::toDto)
                .toList();
    }
}
