package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.exception.BadCredentialsException;
import io.github.aquerr.rentacar.application.security.UserCredentials;
import io.github.aquerr.rentacar.application.security.dto.AuthResult;
import io.github.aquerr.rentacar.util.TestResourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AuthRestController.class, RestErrorController.class})
class AuthMvcControllerTest extends BaseMvcIntegrationTest
{
    @Autowired
    private AuthRestController authRestController;
    @Autowired
    private RestErrorController restErrorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authRestController, restErrorController)
                .build();
    }

    @Test
    void shouldAuthenticateAndReturnOkAndJwtTokenResponse() throws Exception
    {
        // given
        UserCredentials userCredentials = new UserCredentials(new UserCredentials.UsernameOrEmail("rentac.testar@tst.pl"), "rentacar", false);
        AuthResult authResult = AuthResult.builder()
                .jwt("dasiemi23io12iadiomicmaicmaoisme21m39adkacmiozmdoiasm")
                .username("admin")
                .authorities(Set.of("EDIT_CARS"))
                .build();
        given(authenticationManager.authenticate(userCredentials)).willReturn(authResult);

        // when
        // then
        mockMvc.perform(request(HttpMethod.POST, "/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"rentac.testar@tst.pl\",\"password\":\"rentacar\",\"rememberMe\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/response_jwt_token.json")));
    }

    @Test
    void shouldReturnRestErrorResponseWhenAuthenticationFacadeThrowsBadCredentialsException() throws Exception
    {
        // given
        given(authenticationManager.authenticate(any(UserCredentials.class))).willThrow(BadCredentialsException.class);
        given(messageService.resolveMessage("auth.error.bad-credentials", List.of()))
                .willReturn("Wrong username or password!");

        // when
        // then
        mockMvc.perform(request(HttpMethod.POST, "/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"rentac.testar@tst.pl\",\"password\":\"rentacar\",\"rememberMe\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/response_error_bad_credentials.json")));
    }

    @Test
    void shouldReturnRestErrorResponseWhenAuthenticationFacadeThrowsBadCredentialsExceptionAndNoMessageServiceReturnsNullForMessageKey() throws Exception
    {
        // given
        given(authenticationManager.authenticate(any(UserCredentials.class))).willThrow(BadCredentialsException.class);
        given(messageService.resolveMessage("auth.error.bad-credentials", List.of())).willReturn(null);

        // when
        // then
        mockMvc.perform(request(HttpMethod.POST, "/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"rentac.testar@tst.pl\",\"password\":\"rentacar\",\"rememberMe\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/response_error_bad_credentials_message_key_not_found.json")));
    }
}