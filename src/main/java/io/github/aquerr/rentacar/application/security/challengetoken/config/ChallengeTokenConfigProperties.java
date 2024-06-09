package io.github.aquerr.rentacar.application.security.challengetoken.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "challenge-token")
@Configuration
public class ChallengeTokenConfigProperties
{
    int cleanBeforeDays = 14;
}
