package io.github.aquerr.rentacar.domain.activation.cleaner;

import io.github.aquerr.rentacar.repository.ActivationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class OldActivationTokenCleaner
{
    private final ActivationTokenRepository activationTokenRepository;

    @Transactional
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)
    public void cleanOldActivationTokens()
    {
        final int cleanBeforeDays = 14;
        ZonedDateTime beforeDateTime = ZonedDateTime.now().minus(cleanBeforeDays, ChronoUnit.DAYS);
        log.info("Deleting activation tokens with expiration date older than {} from database.", beforeDateTime);
        activationTokenRepository.deleteAllByExpirationDateTimeBefore(beforeDateTime);
    }
}
