package io.github.aquerr.rentacar.application.config.cleaner;

import lombok.Value;

import java.time.Duration;

@Value
public class CleanerConfig
{
    Duration cleanBefore;
}
