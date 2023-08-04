package io.github.aquerr.rentacar.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.aquerr.rentacar.application.security.JwtToken;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class JwtTokenResponse
{
    @JsonProperty("jwt")
    String jwt;
    @JsonProperty("username")
    String username;
    @JsonProperty("authorities")
    Set<String> authorities;

    public static JwtTokenResponse of(JwtToken jwtToken)
    {
        return JwtTokenResponse.of(jwtToken.getJwt(), jwtToken.getUsername(), jwtToken.getAuthorities());
    }
}