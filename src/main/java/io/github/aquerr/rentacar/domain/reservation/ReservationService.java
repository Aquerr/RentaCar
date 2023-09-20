package io.github.aquerr.rentacar.domain.reservation;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.domain.reservation.converter.ReservationConverter;
import io.github.aquerr.rentacar.domain.reservation.dto.Reservation;
import io.github.aquerr.rentacar.domain.reservation.model.ReservationEntity;
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

    public Reservation createNewReservation(Reservation reservation) {
        ReservationEntity reservationEntity = reservationConverter.toReservationEntity(reservation);
        return reservationConverter.toReservationDto(this.reservationRepository.save(reservationEntity));
    }

    public Reservation getReservation(Long reservationId) {
        return reservationConverter.toReservationDto(this.reservationRepository.findById(reservationId).orElse(null));
    }

    public List<Reservation> getMyselfReservation() {
        AuthenticatedUser authenticatedUser = authenticationFacade.getCurrentUser();
        return reservationConverter.toReservationDto(this.reservationRepository.findAllByUserId(authenticatedUser.getProfileId()));
    }
}
