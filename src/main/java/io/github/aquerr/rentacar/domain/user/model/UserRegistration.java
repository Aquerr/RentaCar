package io.github.aquerr.rentacar.domain.user.model;

import lombok.Value;

@Value
public class UserRegistration
{
    String username;
    String email;
    String password;
}
