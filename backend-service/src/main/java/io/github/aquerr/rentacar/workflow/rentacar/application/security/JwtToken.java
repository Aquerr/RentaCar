package io.github.aquerr.rentacar.workflow.rentacar.application.security;

import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class JwtToken
{
    String jwt;
    String username;
    Set<String> authorities;
}
