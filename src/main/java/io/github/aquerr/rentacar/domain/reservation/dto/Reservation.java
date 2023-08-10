package io.github.aquerr.rentacar.domain.reservation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Reservation {
    private Integer id;
    private Integer vehicleId;
    private Long userId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String status;
}
