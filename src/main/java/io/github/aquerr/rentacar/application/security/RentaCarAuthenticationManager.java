package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.exception.BadCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RentaCarAuthenticationManager
{
    private final RentaCarUserDetailsService rentaCarUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public Authentication authenticate(UserCredentials.UsernameOrEmail login, String password)
    {
        AuthenticatedUser authenticatedUser = rentaCarUserDetailsService.loadUserByUsername(login.getValue());
        if (!passwordEncoder.matches(password, authenticatedUser.getPassword()))
        {
            throw new BadCredentialsException();
        }
        return UsernamePasswordAuthenticationToken.authenticated(authenticatedUser, login, authenticatedUser.getAuthorities());
    }
}
