package io.github.aquerr.rentacar.domain.user.dto;

import lombok.Value;

@Value
public class UserRegistration
{
    String username;
    String email;
    String password;
}
