package io.github.aquerr.rentacar.application.config.security.jwt;

import io.github.aquerr.rentacar.repository.InvalidJwtTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class InvalidJwtCleaner
{
    private final InvalidJwtTokenRepository invalidJwtTokenRepository;

    @Transactional
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)
    public void cleanInvalidJwts()
    {
        log.info("Deleting old invalid JWT tokens from database.");
        invalidJwtTokenRepository.deleteAllByExpirationDateTimeBeforeNow();
    }
}
