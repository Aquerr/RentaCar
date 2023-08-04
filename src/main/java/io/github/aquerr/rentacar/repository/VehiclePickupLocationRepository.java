package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.pickup.model.VehiclePickupLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface VehiclePickupLocationRepository extends JpaRepository<VehiclePickupLocationEntity, Integer>
{
    List<VehiclePickupLocationEntity> findAllByLangCodeIn(Set<String> langCodes);
}
