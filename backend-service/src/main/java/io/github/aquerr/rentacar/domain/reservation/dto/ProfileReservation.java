package io.github.aquerr.rentacar.domain.reservation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ProfileReservation {
    private Long id;
    private String vehicleIconUrl;
    private String vehicleName;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String status;
}
