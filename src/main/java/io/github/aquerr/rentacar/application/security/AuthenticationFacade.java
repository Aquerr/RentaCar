package io.github.aquerr.rentacar.application.security;

public interface AuthenticationFacade
{
    AuthenticatedUser getCurrentUser();

    JwtToken authenticate(UserCredentials userCredentials);
}
