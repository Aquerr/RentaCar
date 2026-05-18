package io.github.aquerr.rentacar.application.security.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class MfaAuthResult
{
    boolean success;
}
