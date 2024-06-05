package io.github.aquerr.rentacar.domain.user.password.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordResetTokenDto
{
    private Long id;
    private Long userId;
    private String token;
    private ZonedDateTime expirationDate;
    private boolean used;
}
