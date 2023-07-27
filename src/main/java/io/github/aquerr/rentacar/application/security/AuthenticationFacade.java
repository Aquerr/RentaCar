package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.web.rest.response.JwtTokenResponse;

public interface AuthenticationFacade
{
    AuthenticatedUser getCurrentUser();

    JwtTokenResponse authenticate(UserCredentials userCredentials);
}
