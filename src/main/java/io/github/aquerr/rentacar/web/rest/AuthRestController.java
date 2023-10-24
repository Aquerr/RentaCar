package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.security.RentaCarAuthenticationManager;
import io.github.aquerr.rentacar.application.security.UserCredentials;
import io.github.aquerr.rentacar.application.security.dto.AuthResult;
import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenParams;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.web.rest.request.ActivationTokenRequest;
import io.github.aquerr.rentacar.web.rest.request.MfaAuthRequest;
import io.github.aquerr.rentacar.web.rest.response.AuthResponse;
import io.github.aquerr.rentacar.web.rest.response.JwtTokenResponse;
import io.github.aquerr.rentacar.web.rest.response.MyselfResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthRestController
{
    private final JwtService jwtService;
    private final AuthenticationFacade authenticationFacade;
    private final ProfileService profileService;
    private final UserService userService;
    private final RentaCarAuthenticationManager rentaCarAuthenticationManager;

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody UserCredentials userCredentials)
    {
        AuthResult authResult = rentaCarAuthenticationManager.authenticate(userCredentials);
        return ResponseEntity.ok(AuthResponse.of(authResult));
    }

    @PostMapping("/mfa")
    public ResponseEntity<JwtTokenResponse> authenticateMfa(@RequestBody MfaAuthRequest mfaAuthRequest)
    {
        return ResponseEntity.ok(JwtTokenResponse.of(rentaCarAuthenticationManager.authenticate(mfaAuthRequest)));
    }

    @GetMapping("/myself")
    public ResponseEntity<MyselfResponse> getMyself()
    {
        AuthenticatedUser authenticatedUser = authenticationFacade.getCurrentUser();
        if (authenticatedUser == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserProfile userProfile = this.profileService.getProfileById(authenticationFacade.getCurrentUser().getProfileId());
        Set<String> authorities = this.authenticationFacade.getCurrentUser().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(MyselfResponse.of(userProfile, authorities));
    }

    @PostMapping("/activation")
    public ResponseEntity<?> activateAccount(@RequestBody ActivationTokenRequest activationTokenRequest)
    {
        this.userService.activateAccount(new ActivationTokenParams(activationTokenRequest.getToken()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resend-activation-email/{login}")
    public ResponseEntity<?> resendActivationEmail(@PathVariable("login") UserCredentials.UsernameOrEmail login)
    {
        this.userService.resendActivationEmail(login);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset/init")
    public ResponseEntity<?> resetPassword(@PathVariable("login") UserCredentials.UsernameOrEmail login)
    {
        //TODO
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
