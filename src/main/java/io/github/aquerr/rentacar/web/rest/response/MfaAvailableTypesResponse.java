package io.github.aquerr.rentacar.web.rest.response;

import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class MfaAvailableTypesResponse
{
    Set<String> mfaTypes;
}
