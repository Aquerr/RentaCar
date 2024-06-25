package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.BaseIntegrationTest;
import io.github.aquerr.rentacar.repository.ChallengeTokenRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import io.github.aquerr.rentacar.web.rest.request.UserRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsersRestControllerTest extends BaseIntegrationTest
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeTokenRepository challengeTokenRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp()
    {
        challengeTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser()
    {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                "test_username",
                "test_email@test.com",
                "test_pass");

        // when
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "http://localhost:" + serverPort + "/api/v1/users/register",
                request,
                Void.class);

        // then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertFalse(response.hasBody());
    }

    @Test
    void shouldReturnErrorWhenUsernameOrEmailIsAlreadyUsed()
    {
        // given
        UserRegistrationRequest request = new UserRegistrationRequest(
                "test_username",
                "test_email@test.com",
                "test_pass");

        restTemplate.postForEntity(
                "http://localhost:" + serverPort + "/api/v1/users/register",
                request,
                Void.class);

        // when
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptLanguageAsLocales(List.of(Locale.of("pl")));

        ResponseEntity<RestErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + serverPort + "/api/v1/users/register",
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                RestErrorResponse.class);

        // then
        assertTrue(response.getStatusCode().is4xxClientError());
        assertThat(response.getBody().getCode()).isEqualTo("USER_EXISTS");
        assertThat(response.getBody().getMessage()).isEqualTo("Nazwa użytkownia lub email już jest w użyciu!");
    }
}