package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.reservation.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
