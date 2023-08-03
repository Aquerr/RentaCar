package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.security.UserCredentials;
import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenParams;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.web.rest.request.ActivationTokenRequest;
import io.github.aquerr.rentacar.web.rest.response.JwtTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthRestController {

    private final JwtService jwtService;
    private final AuthenticationFacade authenticationFacade;
    private final ProfileService profileService;
    private final UserService userService;

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
        return ResponseEntity.ok(this.profileService.getProfileById(authenticationFacade.getCurrentUser().getProfileId()));
    }

    @PostMapping("/activation")
    public ResponseEntity<?> activateAccount(@RequestBody ActivationTokenRequest activationTokenRequest)
    {
        this.userService.activateAccount(new ActivationTokenParams(activationTokenRequest.getToken()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invalidate")
    public ResponseEntity<?> invalidate(HttpServletRequest httpServletRequest)
    {
        String jwt = jwtService.getJwtToken(httpServletRequest);
        jwtService.invalidate(jwt);
        return ResponseEntity.ok().build();
    }
}
