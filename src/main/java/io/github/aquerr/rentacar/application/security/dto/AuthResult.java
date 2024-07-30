package io.github.aquerr.rentacar.application.security.dto;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResult
{
    private String username;

    @Nullable
    private String mfaChallenge;

    @Nullable
    private String jwt;

    @Nullable
    private String qrCodeUri;

    @Nullable
    private Set<String> authorities = new HashSet<>();

    @Nullable
    private Authentication authentication;

    private Status status;

    public static AuthResult authenticated(AuthenticatedUser authenticatedUser)
    {
        return AuthResult.builder()
                .authentication(UsernamePasswordAuthenticationToken.authenticated(authenticatedUser, authenticatedUser.getUsername(), authenticatedUser.getAuthorities()))
                .username(authenticatedUser.getUsername())
                .status(Status.AUTHENTICATED)
                .build();
    }

    public static AuthResult requiresMfa(AuthenticatedUser authenticatedUser, String mfaChallenge)
    {
        return AuthResult.builder()
            .mfaChallenge(mfaChallenge)
            .authentication(UsernamePasswordAuthenticationToken.unauthenticated(authenticatedUser, authenticatedUser.getUsername()))
            .status(Status.REQUIRES_MFA)
            .build();
    }

    public static AuthResult badCredentials()
    {
        return AuthResult.builder()
                .status(Status.BAD_CREDENTIALS)
                .build();
    }

    public enum Status
    {
        AUTHENTICATED,
        REQUIRES_MFA,
        BAD_CREDENTIALS
    }
}
