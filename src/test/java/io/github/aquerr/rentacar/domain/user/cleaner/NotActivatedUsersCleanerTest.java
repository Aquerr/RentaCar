package io.github.aquerr.rentacar.domain.user.cleaner;

import io.github.aquerr.rentacar.BaseIntegrationTest;
import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.repository.ActivationTokenRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotActivatedUsersCleanerTest extends BaseIntegrationTest
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivationTokenRepository activationTokenRepository;

    @Autowired
    private NotActivatedUsersCleaner notActivatedUsersCleaner;

    @BeforeEach
    public void setUp()
    {
        activationTokenRepository.deleteAll();
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
        assertThat(activationTokenRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).hasSize(1);
    }

    private void givenExpiredActivationToken(Long userId)
    {
        ActivationTokenEntity activationTokenEntity = new ActivationTokenEntity();
        activationTokenEntity.setUserId(userId);
        activationTokenEntity.setUsed(false);
        activationTokenEntity.setToken("test");
        activationTokenEntity.setExpirationDate(ZonedDateTime.now().minusDays(15));
        activationTokenRepository.saveAndFlush(activationTokenEntity);
    }
}