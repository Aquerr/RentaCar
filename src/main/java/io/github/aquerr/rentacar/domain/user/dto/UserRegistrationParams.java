package io.github.aquerr.rentacar.domain.user.dto;

import lombok.Value;

@Value
public class UserRegistrationParams
{
    String username;
    String email;
    String password;
}
