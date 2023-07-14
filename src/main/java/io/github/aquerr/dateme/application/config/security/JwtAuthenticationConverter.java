package io.github.aquerr.dateme.application.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtAuthenticationConverter implements AuthenticationConverter {
    private static final String BEARER = "Bearer ";

    @Override
    public Authentication convert(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(jwt -> jwt.startsWith(BEARER))
                .map(jwt -> jwt.substring(BEARER.length()))
                .map(jwt -> new UsernamePasswordAuthenticationToken(jwt, jwt))
                .orElse(null);
    }
}
