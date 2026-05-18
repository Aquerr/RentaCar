package io.github.aquerr.rentacar.workflow.rentacar.application.security.rest.response;

import io.github.aquerr.rentacar.workflow.rentacar.application.security.mfa.MfaType;
import lombok.Value;

@Value(staticConstructor = "of")
public class MfaSettingsResponse
{
    MfaType mfaType;
    boolean verified;
    String verifiedDate;
}
