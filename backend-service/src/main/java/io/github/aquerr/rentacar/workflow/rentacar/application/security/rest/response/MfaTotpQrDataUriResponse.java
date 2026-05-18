package io.github.aquerr.rentacar.workflow.rentacar.application.security.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value(staticConstructor = "of")
public class MfaTotpQrDataUriResponse
{
    @JsonProperty("qrDataUri")
    String qrDataUri;
}
