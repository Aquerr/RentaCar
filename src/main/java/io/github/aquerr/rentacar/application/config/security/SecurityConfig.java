package io.github.aquerr.rentacar.application.config.security;

import io.github.aquerr.rentacar.application.config.filter.ApiExceptionFilter;
import io.github.aquerr.rentacar.application.config.filter.JwtAuthenticationFilter;
import io.github.aquerr.rentacar.application.security.RentaCarUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.logbook.servlet.LogbookFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @EnableMethodSecurity
    @ConditionalOnProperty(value = "rentacar.security.enabled", matchIfMissing = true, havingValue = "true")
    @Configuration
    public static class EnabledSecurityConfiguration {

        @Bean
        public SecurityFilterChain securityFilterChain(Environment environment,
                                                       HttpSecurity http,
                                                       RentaCarUserDetailsService rentaCarUserDetailsService,
                                                       JwtAuthenticationFilter jwtAuthenticationFilter,
                                                       ApiExceptionFilter apiExceptionFilter,
                                                       LogbookFilter logbookFilter) throws Exception {

            http.authorizeHttpRequests(requests -> {
                if (environment.matchesProfiles("dev")) {
                    requests.requestMatchers(PathRequest.toH2Console()).permitAll();
                }
                requests.requestMatchers(HttpMethod.GET, "/api/v1/assets/images/{kind}/{name}").permitAll();
                requests.requestMatchers("/api/v1/vehicles/**").permitAll();
                requests.requestMatchers("/api/v1/users/register").permitAll();
                requests.requestMatchers("/api/v1/pickup-locations").permitAll();
                requests.requestMatchers("/api/v1/auth").permitAll();
                requests.requestMatchers("/api/v1/auth/activation").permitAll();
                requests.requestMatchers("/api/v1/auth/resend-activation-email/{login}").permitAll();
                requests.requestMatchers("/api/v1/auth/password-reset/**").permitAll();
                requests.requestMatchers("/api/v1/auth/mfa").permitAll();
                requests.requestMatchers("/api/**").authenticated();
                requests.requestMatchers("/static/**").permitAll();
                requests.requestMatchers("/public/**").permitAll();
                requests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                requests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                requests.requestMatchers("/assets/**").permitAll();
                requests.requestMatchers("/*").permitAll();
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(apiExceptionFilter, JwtAuthenticationFilter.class)
            .addFilterBefore(logbookFilter, ApiExceptionFilter.class)
            .userDetailsService(rentaCarUserDetailsService)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .exceptionHandling(exceptionHandling -> {
                exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
            })
            .cors(Customizer.withDefaults())
            .headers(header -> {
                if (environment.matchesProfiles("dev")) {
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                }
            })
            .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name(), HttpMethod.PUT.name()));
            corsConfiguration.setAllowedOrigins(List.of("*"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }
    }

    @EnableWebSecurity
    @ConditionalOnProperty(value = "rentacar.security.enabled", matchIfMissing = true, havingValue = "false")
    @Configuration
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
            corsConfiguration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name(), HttpMethod.PUT.name()));
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }
    }
}