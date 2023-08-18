package io.github.aquerr.rentacar.web.rest;


import io.github.aquerr.rentacar.application.config.security.jwt.JwtAuthenticationFilter;
import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.application.lang.AcceptedLanguageLocaleMapper;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.i18n.MessageService;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class BaseRestIntegrationTest
{
    @MockBean
    protected JwtService jwtService;
    @MockBean
    protected MessageService messageService;
    @MockBean
    protected ProfileService profileService;
    @MockBean
    protected UserService userService;
    @MockBean
    protected JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    protected AcceptedLanguageLocaleMapper acceptedLanguageLocaleMapper;
}
