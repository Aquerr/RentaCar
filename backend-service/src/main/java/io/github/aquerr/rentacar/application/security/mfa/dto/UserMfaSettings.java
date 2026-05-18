package io.github.aquerr.rentacar.application.security.mfa.dto;

import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserMfaSettings
{
    private Long id;
    private Long userId;
    private MfaType mfaType;
    private boolean verified;
    private ZonedDateTime verifiedDate;
}
