package io.github.aquerr.rentacar.web.rest.response;

import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import lombok.Value;

@Value(staticConstructor = "of")
public class MfaSettingsResponse
{
    MfaType mfaType;
    boolean active;
}
