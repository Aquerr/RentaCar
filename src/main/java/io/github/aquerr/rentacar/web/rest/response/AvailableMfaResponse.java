package io.github.aquerr.rentacar.web.rest.response;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class AvailableMfaResponse
{
    List<String> availableAuthTypes;
}
