package io.github.aquerr.rentacar.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class MyselfResponse
{
    @JsonProperty("userProfile")
    UserProfile userProfile;

    @JsonProperty("authorities")
    Set<String> authorities;
}
