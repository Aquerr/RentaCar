package io.github.aquerr.dateme.application.config.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DateMeAuthenticationManager implements AuthenticationManager {

    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return Optional.ofNullable(authentication)
                .map(auth -> jwtService.validateJwt(String.valueOf(auth.getCredentials())))
                .map(jws -> new UsernamePasswordAuthenticationToken(jws.getBody().getSubject(),
                        String.valueOf(authentication.getCredentials()),
                        List.of(new SimpleGrantedAuthority("USER"))))
                .orElse(null);
    }
}
