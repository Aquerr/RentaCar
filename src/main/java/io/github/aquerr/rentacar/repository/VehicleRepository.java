package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {

    @Query("SELECT vehicle.id FROM VehicleEntity vehicle")
    List<Integer> findAllIds();

    Page<VehicleEntity> findAllByIdIn(Iterable<Integer> ids, Pageable pageable);

    List<VehicleEntity> findByBrandAndModel(String brand, String model);
}
