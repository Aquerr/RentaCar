package io.github.aquerr.rentacar.application.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "login-restrictions")
@Getter
@Setter
public class LoginRestrictionsProperties
{
    int maxFailedLoginAttempts;
}
