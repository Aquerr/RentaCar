package io.github.aquerr.rentacar.domain.reservation;

import io.github.aquerr.rentacar.application.exception.ReservationException;
import io.github.aquerr.rentacar.application.exception.ReservationVehicleNotAvailableException;
import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.domain.reservation.converter.ReservationConverter;
import io.github.aquerr.rentacar.domain.reservation.dto.Reservation;
import io.github.aquerr.rentacar.domain.reservation.model.ReservationEntity;
import io.github.aquerr.rentacar.domain.reservation.model.ReservationStatus;
import io.github.aquerr.rentacar.domain.vehicle.VehicleService;
import io.github.aquerr.rentacar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationConverter reservationConverter;
    private final AuthenticationFacade authenticationFacade;
    private final VehicleService vehicleService;

    public Reservation save(Reservation reservation) {
        if (vehicleService.isVehicleAvailable(reservation.getVehicleId(), reservation.getDateFrom(), reservation.getDateTo())) {
            ReservationEntity reservationEntity = reservationConverter.toReservationEntity(reservation);
            return reservationConverter.toReservationDto(this.reservationRepository.save(reservationEntity));
        }
        if (reservation.getId() != null) {
            if (!ReservationStatus.CANCELLED.getStatus().equals(reservation.getStatus())) {
                reservation.setStatus(ReservationStatus.VEHICLE_NOT_AVAILABLE.getStatus());
            }
            ReservationEntity reservationEntity = reservationConverter.toReservationEntity(reservation);
            return reservationConverter.toReservationDto(this.reservationRepository.save(reservationEntity));
        }
        throw new ReservationVehicleNotAvailableException();
    }

    public Reservation getReservation(Long reservationId) {
        List<Reservation> userReservations = getMyReservations();
        return userReservations.stream()
                .filter(reservation -> reservationId.equals(reservation.getId()))
                .findFirst()
                .orElseThrow(ReservationException::new);
    }

    public List<Reservation> getMyReservations() {
        AuthenticatedUser authenticatedUser = authenticationFacade.getCurrentUser();
        return reservationConverter.toReservationDto(this.reservationRepository.findAllByUserId(authenticatedUser.getProfileId()));
    }
}
