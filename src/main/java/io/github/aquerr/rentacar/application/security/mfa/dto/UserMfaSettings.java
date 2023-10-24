package io.github.aquerr.rentacar.application.security.mfa.dto;

import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import lombok.Data;

@Data
public class UserMfaSettings
{
    private Long id;
    private Long credentialsId;
    private MfaType mfaType;
    private boolean verified;
}
