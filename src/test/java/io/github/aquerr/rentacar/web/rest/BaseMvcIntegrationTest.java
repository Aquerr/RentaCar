package io.github.aquerr.rentacar.web.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aquerr.rentacar.application.config.LogBookConfig;
import io.github.aquerr.rentacar.application.config.security.SecurityConfig;
import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.application.lang.AcceptedLanguageLocaleMapper;
import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.security.RentaCarAuthenticationManager;
import io.github.aquerr.rentacar.application.security.RentaCarUserDetailsService;
import io.github.aquerr.rentacar.application.security.SecurityManager;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.domain.user.password.PasswordResetService;
import io.github.aquerr.rentacar.i18n.MessageService;
import io.github.aquerr.rentacar.repository.InvalidJwtTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Import(value = {JwtService.class, SecurityManager.class, SecurityConfig.class, LogBookConfig.class})
public abstract class BaseMvcIntegrationTest
{
    @Autowired
    protected RestErrorController restErrorController;
    @Autowired
    protected JwtService jwtService;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected SecurityManager securityManager;

    @MockBean
    protected InvalidJwtTokenRepository invalidJwtTokenRepository;
    @MockBean
    protected MessageService messageService;
    @MockBean
    protected ProfileService profileService;
    @MockBean
    protected UserService userService;
    @MockBean
    protected RentaCarUserDetailsService rentaCarUserDetailsService;
    @MockBean
    protected AcceptedLanguageLocaleMapper acceptedLanguageLocaleMapper;
    @MockBean
    protected AuthenticationFacade authenticationFacade;
    @MockBean
    protected RentaCarAuthenticationManager authenticationManager;
    @MockBean
    protected PasswordResetService passwordResetService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @BeforeEach
    void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    protected AuthenticatedUser prepareAuthenticatedUser(long id, String username, long profileId, Set<? extends GrantedAuthority> authorities)
    {
        return new AuthenticatedUser(id, username, "test_pass", "remote_addr", authorities);
    }
}
