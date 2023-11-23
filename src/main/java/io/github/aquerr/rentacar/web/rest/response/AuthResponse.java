package io.github.aquerr.rentacar.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.aquerr.rentacar.application.security.dto.AuthResult;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class AuthResponse
{
    @Nullable
    @JsonProperty("jwt")

    String jwt;
    @JsonProperty("username")
    String username;

    @Nullable
    @JsonProperty("authorities")
    Set<String> authorities;

    String status;

    @Nullable
    String mfaChallenge;

    public static AuthResponse of(AuthResult authResult)
    {
        return AuthResponse.builder()
                .status(resolveHttpStatus(authResult.getStatus()))
                .jwt(authResult.getJwt())
                .authorities(authResult.getAuthorities())
                .mfaChallenge(authResult.getMfaChallenge())
                .username(authResult.getUsername())
                .build();
    }

    private static String resolveHttpStatus(AuthResult.Status status)
    {
        if (status == AuthResult.Status.AUTHENTICATED)
            return HttpStatus.OK.getReasonPhrase();
        else if (status == AuthResult.Status.BAD_CREDENTIALS)
            return HttpStatus.BAD_REQUEST.getReasonPhrase();
        else if (status == AuthResult.Status.REQUIRES_MFA)
            return AuthResult.Status.REQUIRES_MFA.name();
        return HttpStatus.BAD_REQUEST.getReasonPhrase();
    }
}
