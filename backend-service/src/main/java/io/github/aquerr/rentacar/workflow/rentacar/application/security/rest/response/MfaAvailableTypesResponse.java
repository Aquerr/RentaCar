package io.github.aquerr.rentacar.workflow.rentacar.application.security.rest.response;

import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class MfaAvailableTypesResponse
{
    Set<String> mfaTypes;
}
