package io.github.aquerr.rentacar.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.aquerr.rentacar.application.security.dto.MfaActivationResult;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class MfaActivationResponse
{
    @JsonProperty("recoveryCodes")
    Set<String> recoveryCodes;

    public static MfaActivationResponse of(MfaActivationResult mfaActivationResult)
    {
        return new MfaActivationResponse(mfaActivationResult.getRecoveryCodes());
    }
}
