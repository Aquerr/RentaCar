package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.web.rest.request.UserCredentials;
import io.github.aquerr.rentacar.web.rest.response.JwtTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthRestController {

    private final JwtService jwtService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public ResponseEntity<JwtTokenResponse> authenticate(@RequestBody UserCredentials userCredentials) {
        return ResponseEntity.ok(authenticationFacade.authenticate(userCredentials));
    }

    @GetMapping("/myself")
    public ResponseEntity<UserProfile> getCurrentUser()
    {
        AuthenticatedUser authenticatedUser = authenticationFacade.getCurrentUser();
        if (authenticatedUser == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(UserProfile.of(authenticationFacade.getCurrentUser()));
    }

    @PostMapping("/invalidate")
    public ResponseEntity<?> invalidate(HttpServletRequest httpServletRequest)
    {
        String jwt = jwtService.getJwtToken(httpServletRequest);
        jwtService.invalidate(jwt);
        return ResponseEntity.ok().build();
    }


    public record UserProfile(String username, List<String> authorities)
    {
        public static UserProfile of(AuthenticatedUser authenticatedUser)
        {
            return new UserProfile(authenticatedUser.getUsername(), authenticatedUser.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());
        }
    }
}
