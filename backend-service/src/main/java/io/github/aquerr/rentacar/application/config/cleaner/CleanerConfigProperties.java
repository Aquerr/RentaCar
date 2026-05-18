package io.github.aquerr.rentacar.application.config.cleaner;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cleaner")
@Getter
@Setter
public class CleanerConfigProperties
{
    CleanerConfig challengeToken;
    CleanerConfig notActivatedUsers;
}
