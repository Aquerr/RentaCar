package io.github.aquerr.rentacar.domain.reservation;

import io.github.aquerr.rentacar.domain.reservation.converter.ReservationConverter;
import io.github.aquerr.rentacar.domain.reservation.dto.Reservation;
import io.github.aquerr.rentacar.domain.reservation.model.ReservationEntity;
import io.github.aquerr.rentacar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationConverter reservationConverter;

    public Reservation createNewReservation(Reservation reservation) {
        ReservationEntity reservationEntity = reservationConverter.toReservationEntity(reservation);
        return reservationConverter.toReservationDto(this.reservationRepository.save(reservationEntity));
    }
}
