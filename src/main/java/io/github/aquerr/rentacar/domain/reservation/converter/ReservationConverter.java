package io.github.aquerr.rentacar.domain.reservation.converter;

import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.reservation.dto.Reservation;
import io.github.aquerr.rentacar.domain.reservation.model.ReservationEntity;
import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {
    public Reservation toReservationDto(ReservationEntity reservationEntity) {
        if (reservationEntity == null) {
            return null;
        }

        return Reservation.builder()
                .id(reservationEntity.getId())
                .vehicleId(reservationEntity.getVehicle().getId())
                .userId(reservationEntity.getUserProfile().getId())
                .dateFrom(reservationEntity.getDateFrom())
                .dateTo(reservationEntity.getDateTo())
                .status(reservationEntity.getStatus())
                .build();
    }

    public ReservationEntity toReservationEntity(Reservation reservationDto) {
        if (reservationDto == null) {
            return null;
        }

        return ReservationEntity.builder()
                .id(reservationDto.getId())
                .vehicle(VehicleEntity.builder()
                        .id(reservationDto.getVehicleId())
                        .build())
                .userProfile(UserProfileEntity.builder()
                        .id(reservationDto.getUserId())
                        .build())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(reservationDto.getStatus())
                .build();
    }
}
