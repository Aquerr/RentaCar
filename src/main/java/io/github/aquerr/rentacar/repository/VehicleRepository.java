package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {
}
