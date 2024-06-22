package io.github.aquerr.rentacar.application.security.challengetoken.cleaner;

import io.github.aquerr.rentacar.application.config.cleaner.CleanerConfigProperties;
import io.github.aquerr.rentacar.repository.ChallengeTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class OldChallengeTokensCleaner
{
    private final ChallengeTokenRepository challengeTokenRepository;
    private final CleanerConfigProperties properties;

    @Transactional
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)
    public void cleanOldChallengeTokens()
    {
        OffsetDateTime beforeDateTime = OffsetDateTime.now().minus(properties.getChallengeToken().getCleanBefore());
        log.info("Deleting challenge tokens with expiration date older than {} from database.", beforeDateTime);
        challengeTokenRepository.deleteAllByExpirationDateTimeBefore(beforeDateTime);
    }
}
