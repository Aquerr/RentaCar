package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.config.security.jwt.JwtAuthenticationFilter;
import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.application.lang.AcceptedLanguageLocaleMapper;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.security.JwtToken;
import io.github.aquerr.rentacar.application.security.UserCredentials;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.i18n.MessageService;
import io.github.aquerr.rentacar.util.TestResourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthRestController.class)
class AuthRestControllerTest
{
    @MockBean
    private AuthenticationFacade authenticationFacade;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private ProfileService profileService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private AcceptedLanguageLocaleMapper acceptedLanguageLocaleMapper;

    @Autowired
    private AuthRestController authRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authRestController)
                .build();
    }

    @Test
    void shouldAuthenticateAndReturnOkAndJwtTokenResponse() throws Exception
    {
        // given
        UserCredentials userCredentials = new UserCredentials(new UserCredentials.UsernameOrEmail("rentac.testar@tst.pl"), "rentacar", false);
        JwtToken expectedResponse = JwtToken.of("dasiemi23io12iadiomicmaicmaoisme21m39adkacmiozmdoiasm", "admin", Set.of("EDIT_CARS"));
        given(authenticationFacade.authenticate(userCredentials)).willReturn(expectedResponse);


        // when
        // then
        mockMvc.perform(request(HttpMethod.POST, "/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"rentac.testar@tst.pl\",\"password\":\"rentacar\",\"rememberMe\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/response_jwt_token.json")));
    }
}