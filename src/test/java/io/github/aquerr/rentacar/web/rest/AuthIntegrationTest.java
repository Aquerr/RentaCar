package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.BaseIntegrationTest;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.password.model.PasswordResetTokenEntity;
import io.github.aquerr.rentacar.repository.PasswordResetTokenRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import io.github.aquerr.rentacar.web.rest.request.InitPasswordResetRequest;
import io.github.aquerr.rentacar.web.rest.request.PasswordResetRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthIntegrationTest extends BaseIntegrationTest
{
    private static final String EMAIL = "Email";
    private static final String TOKEN = "Token";

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp()
    {
        userCredentialsRepository.deleteAll();
        passwordResetTokenRepository.deleteAll();
    }

    @Test
    void shouldInitPasswordReset()
    {
        // given
        UserCredentialsEntity userCredentialsEntity = UserCredentialsEntity.builder()
                .username("username")
                .email(EMAIL)
                .password("test")
                .activated(true)
                .activatedDateTime(ZonedDateTime.of(LocalDateTime.of(2024, 4, 20, 10, 15), ZoneId.of("Europe/Warsaw")))
                .build();
        userCredentialsEntity = userCredentialsRepository.save(userCredentialsEntity);

        // when
        InitPasswordResetRequest initPasswordResetRequest = new InitPasswordResetRequest(EMAIL);
        ResponseEntity<?> responseEntity = testRestTemplate.postForEntity("http://localhost:" + serverPort + "/api/v1/auth/password-reset/init", initPasswordResetRequest, Object.class);

        // then
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(passwordResetTokenRepository.findAllByCredentialsIdIn(List.of(userCredentialsEntity.getId()))).hasSize(1);
    }

    @Test
    void shouldChangeUserPassword()
    {
        // given
        UserCredentialsEntity userCredentialsEntity = UserCredentialsEntity.builder()
                .username("username")
                .email(EMAIL)
                .password("test")
                .activated(true)
                .activatedDateTime(ZonedDateTime.of(LocalDateTime.of(2024, 4, 20, 10, 15), ZoneId.of("Europe/Warsaw")))
                .build();
        userCredentialsEntity = userCredentialsRepository.save(userCredentialsEntity);

        PasswordResetTokenEntity entity = PasswordResetTokenEntity.builder()
                .credentialsId(userCredentialsEntity.getId())
                .used(false)
                .token(TOKEN)
                .expirationDate(ZonedDateTime.now().plus(1, ChronoUnit.HOURS))
                .build();
        passwordResetTokenRepository.save(entity);

        // when

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest(TOKEN, "new-password");
        ResponseEntity<?> responseEntity = testRestTemplate.postForEntity("http://localhost:" + serverPort + "/api/v1/auth/password-reset", passwordResetRequest, Object.class);

        // then
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(userCredentialsRepository.findByEmail(EMAIL).getPassword()).isEqualTo("new-password");
    }
}
