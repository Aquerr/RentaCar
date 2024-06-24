package io.github.aquerr.rentacar.domain.user.cleaner;

import io.github.aquerr.rentacar.BaseIntegrationTest;
import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.repository.ChallengeTokenRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotActivatedUsersCleanerTest extends BaseIntegrationTest
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeTokenRepository challengeTokenRepository;

    @Autowired
    private NotActivatedUsersCleaner notActivatedUsersCleaner;

    @BeforeEach
    public void setUp()
    {
        challengeTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCleanNotActivatedUsers()
    {
        // given
        UserEntity notActivatedUser = givenActivatedUser(UserCredentialsEntity.builder()
                .activated(false)
                .username("test")
                .email("testing@yolo.com")
                .password(passwordEncoder.encode("my_pass"))
                .build());
        givenActivatedUser();
        givenExpiredActivationToken(notActivatedUser.getId());

        // when
        notActivatedUsersCleaner.cleanNotActivatedUsersAndTokens();

        // then
        assertThat(challengeTokenRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).hasSize(1);
    }

    private void givenExpiredActivationToken(Long userId)
    {
        ChallengeTokenEntity activationTokenEntity = ChallengeTokenEntity.builder()
                .userId(userId)
                .operationType(OperationType.ACCOUNT_ACTIVATION)
                .used(false)
                .token("test")
                .expirationDate(OffsetDateTime.now().minusDays(15))
                .build();
        challengeTokenRepository.saveAndFlush(activationTokenEntity);
    }
}