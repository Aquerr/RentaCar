package io.github.aquerr.rentacar.web.rest.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

@Value
public class ActivationTokenRequest
{
    String token;

    @JsonCreator
    public ActivationTokenRequest(String token)
    {
        this.token = token;
    }
}
