package io.github.aquerr.rentacar.application.security.rest.request;

import lombok.Value;

@Value
public class PasswordResetRequest
{
    String token;
    String password;
}
