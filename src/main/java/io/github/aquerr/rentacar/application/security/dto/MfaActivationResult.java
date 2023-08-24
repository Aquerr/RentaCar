package io.github.aquerr.rentacar.application.security.dto;

import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class MfaActivationResult
{
    Set<String> recoveryCodes;
}
