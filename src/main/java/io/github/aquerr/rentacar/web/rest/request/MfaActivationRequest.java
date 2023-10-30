package io.github.aquerr.rentacar.web.rest.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class MfaActivationRequest
{
    @JsonProperty("code")
    String code;

    @JsonCreator
    public MfaActivationRequest(String code)
    {
        this.code = code;
    }
}
