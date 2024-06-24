package io.github.aquerr.rentacar.domain.user.cleaner;

import io.github.aquerr.rentacar.application.config.cleaner.CleanerConfigProperties;
import io.github.aquerr.rentacar.application.security.challengetoken.ChallengeTokenService;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class NotActivatedUsersCleaner
{

    private final UserCredentialsRepository userCredentialsRepository;
    private final ChallengeTokenService challengeTokenService;
    private final UserRepository userRepository;
    private final CleanerConfigProperties cleanerConfigProperties;

    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cleanNotActivatedUsersAndTokens()
    {
        OffsetDateTime beforeDateTime = OffsetDateTime.now().minus(cleanerConfigProperties.getNotActivatedUsers().getCleanBefore());

        List<UserCredentialsEntity> userCredentialsEntities = userCredentialsRepository.findAllNotActivatedUserCredentialsBefore(beforeDateTime);
        List<Long> userCredentialsIds = userCredentialsEntities.stream().map(UserCredentialsEntity::getUserId).toList();

        log.info("Deleting user credentials due to not activated account for long time period for {}", userCredentialsEntities.stream().map(UserCredentialsEntity::getEmail).toList());

        challengeTokenService.deleteForUsers(userCredentialsIds, OperationType.ACCOUNT_ACTIVATION);
        userRepository.deleteAllById(userCredentialsIds);
    }
}
