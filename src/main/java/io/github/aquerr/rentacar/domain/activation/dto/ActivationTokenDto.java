package io.github.aquerr.rentacar.domain.activation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActivationTokenDto
{
    private Long id;
    private Long credentialsId;
    private String token;
    private ZonedDateTime expirationDate;
    private boolean used;
}
