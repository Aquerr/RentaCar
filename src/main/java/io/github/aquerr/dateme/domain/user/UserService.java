package io.github.aquerr.dateme.domain.user;

import io.github.aquerr.dateme.application.config.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final JwtService jwtService;

    public String authenticate(String username, String password) {
        //TODO: Verify credentials with database...

        return jwtService.createJwt("testuser");
    }
}
