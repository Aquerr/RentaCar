package io.github.aquerr.dateme.application.config.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class SecurityConfig {

    @EnableWebSecurity
    @ConditionalOnProperty(value = "dateme.security.enabled", havingValue = "true")
    public static class EnabledSecurityConfiguration {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                       AuthenticationManager authenticationManager,
                                                       JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
            AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, jwtAuthenticationConverter);

            http.authorizeHttpRequests(requests -> {
                requests.requestMatchers("/api/v1/auth").permitAll();
                requests.requestMatchers("/api/v1/ws/**").permitAll();
                requests.requestMatchers("/api/**").authenticated();
                requests.requestMatchers("/static/**").permitAll();
                requests.requestMatchers("/public/**").permitAll();
                requests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                requests.requestMatchers("/*").permitAll();
            })
            .addFilterAt(authenticationFilter, AuthenticationFilter.class)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .exceptionHandling(exceptionHandling -> {
                exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
            })
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(JwtService jwtService) {
            return new DateMeAuthenticationManager(jwtService);
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name()));
            corsConfiguration.setAllowedOrigins(List.of("*"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }
    }

    @EnableWebSecurity
    @ConditionalOnProperty(value = "dateme.security.enabled", matchIfMissing = true, havingValue = "false")
    public static class DisabledSecurityConfiguration {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.cors(Customizer.withDefaults())
                    .headers(headerSpec -> headerSpec.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                    .authorizeHttpRequests(requests -> requests.requestMatchers("/**").permitAll())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name()));
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }
    }
}