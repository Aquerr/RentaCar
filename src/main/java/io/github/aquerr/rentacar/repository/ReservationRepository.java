package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.reservation.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {


    @Query("SELECT reservation.vehicle.id FROM ReservationEntity reservation " +
            "WHERE (:dateTo >= reservation.dateFrom AND :dateTo <= reservation.dateTo) " +
            "OR (:dateFrom >= reservation.dateFrom AND :dateFrom <= reservation.dateTo)")
    List<Integer> findAllNotAvailableVehiclesBetweenDates(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
