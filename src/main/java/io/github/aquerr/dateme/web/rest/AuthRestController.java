package io.github.aquerr.dateme.web.rest;

import io.github.aquerr.dateme.domain.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthRestController {

    private final UserService userService;

    @PostMapping
    public JwtTokenResponse authenticate(@RequestBody UserCredentials userCredentials) {
        return JwtTokenResponse.of(userService.authenticate(userCredentials.getUsername(), userCredentials.getPassword()));
    }

    @Value
    static class UserCredentials {
        String username;
        String password;
    }

    @Value(staticConstructor = "of")
    private static class JwtTokenResponse {
        String value;
    }
}
