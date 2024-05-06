package io.github.aquerr.rentacar.web.rest.request;

import lombok.Value;

@Value
public class PasswordResetRequest
{
    String token;
    String password;
}
