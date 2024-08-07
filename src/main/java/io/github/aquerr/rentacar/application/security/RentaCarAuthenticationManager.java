package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.config.security.jwt.JwtService;
import io.github.aquerr.rentacar.application.exception.BadCredentialsException;
import io.github.aquerr.rentacar.application.exception.LoginBlockedException;
import io.github.aquerr.rentacar.application.exception.MfaRequiredException;
import io.github.aquerr.rentacar.application.security.dto.AuthResult;
import io.github.aquerr.rentacar.application.security.dto.MfaActivationResult;
import io.github.aquerr.rentacar.application.security.mfa.MfaAuthenticationService;
import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import io.github.aquerr.rentacar.application.security.mfa.dto.UserMfaSettings;
import io.github.aquerr.rentacar.web.rest.request.MfaAuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RentaCarAuthenticationManager
{
    private final RentaCarUserDetailsService rentaCarUserDetailsService;
    private final MfaAuthenticationService mfaAuthenticationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final LoginRestrictionsService loginRestrictionsService;

    public MfaActivationResult activateMfa(AuthenticatedUser authenticatedUser, String code)
    {
        return mfaAuthenticationService.activate(authenticatedUser.getId(), code);
    }

    public AuthResult authenticate(UserCredentials userCredentials, String ipAddress)
    {
        AuthenticatedUser authenticatedUser = null;
        try
        {
            if (!loginRestrictionsService.canLoginFromIp(ipAddress))
                throw new LoginBlockedException();

            authenticatedUser = rentaCarUserDetailsService.loadUserByUsername(userCredentials.getLogin());

            if (!loginRestrictionsService.canLogin(authenticatedUser.getUsername()))
                throw new LoginBlockedException();


            AuthResult authResult = authenticateByPassword(authenticatedUser, userCredentials.getPassword());
            JwtToken jwtToken = createJwtAndSetAuth(authResult, userCredentials.isRememberMe());
            return AuthResult.builder()
                    .jwt(jwtToken.getJwt())
                    .authorities(jwtToken.getAuthorities())
                    .username(jwtToken.getUsername())
                    .status(AuthResult.Status.AUTHENTICATED)
                    .authentication(authResult.getAuthentication())
                    .build();
        }
        catch (MfaRequiredException exception)
        {
            return authResultWithMfaChallenge(authenticatedUser);
        }
        catch (Exception exception)
        {
            if (exception instanceof BadCredentialsException)
            {
                if (authenticatedUser != null)
                {
                    loginRestrictionsService.incrementFailedLoginForUsername(authenticatedUser.getUsername());
                }
                loginRestrictionsService.incrementFailedLoginForIpAddress(ipAddress);
            }
            throw exception;
        }
    }

    public JwtToken authenticate(MfaAuthRequest mfaAuthRequest, String ipAddress)
    {
        AuthResult authResult = mfaAuthenticationService.authenticate(mfaAuthRequest, ipAddress);

        //TODO: Handle RememberMe
        return createJwtAndSetAuth(authResult, true);
    }

    public UserMfaSettings getUserMfaSettings(AuthenticatedUser authenticatedUser)
    {
        return mfaAuthenticationService.getUserMfaSettings(authenticatedUser.getId()).orElse(null);
    }


    private JwtToken createJwtAndSetAuth(AuthResult authResult, boolean rememberMe)
    {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authResult.getAuthentication().getPrincipal();
        Set<String> authorities = authenticatedUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return JwtToken.of(jwtService.createJwt(authenticatedUser.getUsername(), rememberMe, authorities),
                authenticatedUser.getUsername(),
                authorities);
    }

    private AuthResult authenticateByPassword(AuthenticatedUser authenticatedUser, String password)
    {
        if (!passwordEncoder.matches(password, authenticatedUser.getPassword()))
        {
            throw new BadCredentialsException();
        }

        if (hasMfaEnabled(authenticatedUser))
        {
            throw new MfaRequiredException();
        }

        return AuthResult.authenticated(authenticatedUser);
    }

    private AuthResult authResultWithMfaChallenge(AuthenticatedUser authenticatedUser)
    {
        String mfaChallenge = this.mfaAuthenticationService.generateMfaChallenge(authenticatedUser);
        return AuthResult.requiresMfa(authenticatedUser, mfaChallenge);
    }

    private boolean hasMfaEnabled(AuthenticatedUser authenticatedUser)
    {
        return mfaAuthenticationService.getUserMfaSettings(authenticatedUser.getId())
                .map(UserMfaSettings::isVerified)
                .orElse(false);
    }

    public String prepareMfaActivation(AuthenticatedUser authenticatedUser, MfaType mfaType)
    {
        return this.mfaAuthenticationService.prepareMfaActivation(authenticatedUser, mfaType);
    }

    public void deleteMfa(AuthenticatedUser authenticatedUser)
    {
        this.mfaAuthenticationService.deleteMfaForUser(authenticatedUser.getId());
    }

    public Set<String> getAvailableMfaTypes()
    {
        return this.mfaAuthenticationService.getAvailableAuthTypes();
    }
}
