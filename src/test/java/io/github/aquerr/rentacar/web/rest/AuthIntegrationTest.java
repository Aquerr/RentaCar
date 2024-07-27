package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.BaseIntegrationTest;
import io.github.aquerr.rentacar.application.config.CacheConfig;
import io.github.aquerr.rentacar.application.security.UserCredentials;
import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.repository.ChallengeTokenRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import io.github.aquerr.rentacar.web.rest.request.InitPasswordResetRequest;
import io.github.aquerr.rentacar.web.rest.request.PasswordResetRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthIntegrationTest extends BaseIntegrationTest
{
    private static final String TOKEN = "Token";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeTokenRepository challengeTokenRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp()
    {
        challengeTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldInitPasswordReset()
    {
        // given
        UserEntity userEntity = givenActivatedUser();

        // when
        InitPasswordResetRequest initPasswordResetRequest = new InitPasswordResetRequest(EMAIL);
        ResponseEntity<?> responseEntity = testRestTemplate.postForEntity("http://localhost:" + serverPort + "/api/v1/auth/password-reset/init", initPasswordResetRequest, Object.class);

        // then
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(challengeTokenRepository.findAllByUserIdIn(List.of(userEntity.getId()))).hasSize(1);
    }

    @Test
    void shouldChangeUserPassword()
    {
        // given
        UserEntity userEntity = givenActivatedUser();

        ChallengeTokenEntity entity = ChallengeTokenEntity.builder()
                .userId(userEntity.getId())
                .operationType(OperationType.PASSWORD_RESET)
                .used(false)
                .token(TOKEN)
                .expirationDate(OffsetDateTime.now().plusHours(1))
                .build();
        challengeTokenRepository.save(entity);

        // when

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest(TOKEN, "new-password");
        ResponseEntity<?> responseEntity = testRestTemplate.postForEntity("http://localhost:" + serverPort + "/api/v1/auth/password-reset", passwordResetRequest, Object.class);

        // then
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertTrue(passwordEncoder.matches("new-password", userRepository.findByCredentials_Email(EMAIL).getCredentials().getPassword()));
    }

    @Test
    void shouldReturnAccountBlockedWhenMultipleFailedLoginAttempts()
    {
        // given
        Cache cache = cacheManager.getCache(CacheConfig.FAILED_LOGIN_ACCOUNTS);
        cache.put("username", 5);

        givenActivatedUser();

        // when
        var response = testRestTemplate.postForEntity("/api/v1/auth",
                new UserCredentials(new UserCredentials.UsernameOrEmail(USERNAME), "wrong_pass", false),
                RestErrorResponse.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(403);
        assertThat(response.getBody().getCode()).isEqualTo("LOGIN_BLOCKED");
    }
}
