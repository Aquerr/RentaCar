package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.reservation.ReservationService;
import io.github.aquerr.rentacar.domain.reservation.dto.Reservation;
import io.github.aquerr.rentacar.domain.reservation.model.ReservationStatus;
import io.github.aquerr.rentacar.web.rest.response.ReservationResponse;
import io.github.aquerr.rentacar.web.rest.response.ReservationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationRestController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(ReservationResponse.of(this.reservationService.save(reservation)));
    }

    @PutMapping
    public ResponseEntity<ReservationResponse> updateReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(ReservationResponse.of(this.reservationService.save(reservation)));
    }

    @PatchMapping("/{reservationId}/status/{status}")
    public ResponseEntity<?> updateReservationStatus(@PathVariable Long reservationId, @PathVariable ReservationStatus status) {
        this.reservationService.updateReservationStatus(reservationId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(ReservationResponse.of(this.reservationService.getReservation(reservationId)));
    }

    @GetMapping("/my-self")
    public ResponseEntity<ReservationsResponse> getProfileReservations() {
        return ResponseEntity.ok(ReservationsResponse.of(this.reservationService.getProfileReservations()));
    }

}
