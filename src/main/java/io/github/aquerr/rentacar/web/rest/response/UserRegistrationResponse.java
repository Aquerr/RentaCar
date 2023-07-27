package io.github.aquerr.rentacar.web.rest.response;

import lombok.Value;

import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class UserRegistrationResponse
{
    String token;
    ZonedDateTime expirationDate;
}
