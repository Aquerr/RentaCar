package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.web.rest.request.UserCredentials;
import io.github.aquerr.rentacar.web.rest.response.JwtTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class RentaCarAuthenticationFacade implements AuthenticationFacade
{
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticatedUser getCurrentUser()
    {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken))
                .map(Authentication::getPrincipal)
                .map(AuthenticatedUser.class::cast)
                .orElse(null);
    }

    @Override
    public JwtTokenResponse authenticate(UserCredentials userCredentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.username(), userCredentials.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        String jwt = jwtService.createJwt(authenticatedUser);

        return new JwtTokenResponse(jwt, authenticatedUser.getUsername(), authenticatedUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
    }
}