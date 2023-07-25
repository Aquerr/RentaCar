package io.github.aquerr.rentacar.domain.profile.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfileDto {
    private Long id;
    private Long userId;
    private String name;
    private LocalDate birthDate;
    private String city;
}
