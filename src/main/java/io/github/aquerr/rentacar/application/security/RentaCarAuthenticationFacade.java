package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RentaCarAuthenticationFacade implements AuthenticationFacade
{
    private final JwtService jwtService;
    private final RentaCarAuthenticationManager rentaCarAuthenticationManager;

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
    public JwtToken authenticate(UserCredentials userCredentials) {
        Authentication authentication = rentaCarAuthenticationManager.authenticate(userCredentials.getLogin(), userCredentials.getPassword());
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.createJwt(authenticatedUser, userCredentials.isRememberMe());

        return JwtToken.of(jwt, authenticatedUser.getUsername(), authenticatedUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));
    }
}
