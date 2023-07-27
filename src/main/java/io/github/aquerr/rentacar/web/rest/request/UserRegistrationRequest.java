package io.github.aquerr.rentacar.web.rest.request;

import lombok.Value;

@Value
public class UserRegistrationRequest
{
    String username;
    String email;
    String password;
}
