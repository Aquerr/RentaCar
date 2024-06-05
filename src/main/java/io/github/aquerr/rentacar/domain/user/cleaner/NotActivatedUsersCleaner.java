package io.github.aquerr.rentacar.domain.user.cleaner;

import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.repository.ActivationTokenRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class NotActivatedUsersCleaner
{

    private final UserCredentialsRepository userCredentialsRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cleanNotActivatedUsersAndTokens()
    {
        final int cleanBeforeDays = 14;
        ZonedDateTime beforeDateTime = ZonedDateTime.now().minus(cleanBeforeDays, ChronoUnit.DAYS);

        List<UserCredentialsEntity> userCredentialsEntities = userCredentialsRepository.findAllNotActivatedUserCredentialsBefore(beforeDateTime);
        List<Long> userCredentialsIds = userCredentialsEntities.stream().map(UserCredentialsEntity::getUserId).toList();
        List<ActivationTokenEntity> userActivationTokens = activationTokenRepository.findAllByUserIdIn(userCredentialsIds);

        log.info("Deleting user credentials due to not activated account for long time period for {}", userCredentialsEntities.stream().map(UserCredentialsEntity::getEmail).toList());

        activationTokenRepository.deleteAllById(userActivationTokens.stream().map(ActivationTokenEntity::getId).toList());
        userRepository.deleteAllById(userCredentialsIds);
    }
}
