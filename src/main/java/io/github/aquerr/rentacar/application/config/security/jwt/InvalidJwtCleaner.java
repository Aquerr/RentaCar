package io.github.aquerr.rentacar.application.config.security.jwt;

import io.github.aquerr.rentacar.repository.InvalidJwtTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class InvalidJwtCleaner
{
    private final InvalidJwtTokenRepository invalidJwtTokenRepository;

    @Transactional
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)
    public void cleanInvalidJwts()
    {
        invalidJwtTokenRepository.deleteAllByExpirationDateTimeBeforeNow();
    }
}
