package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.exception.UserLockedException;
import io.github.aquerr.rentacar.application.exception.UserNotVerifiedException;
import io.github.aquerr.rentacar.domain.profile.model.RentaCarUserProfile;
import io.github.aquerr.rentacar.domain.user.model.RentaCarUserCredentials;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@AllArgsConstructor
public class RentaCarUserDetailsService implements UserDetailsService
{
    private final UserCredentialsRepository userCredentialsRepository;
    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public AuthenticatedUser loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserCredentials.UsernameOrEmail usernameOrEmail = new UserCredentials.UsernameOrEmail(username);

        RentaCarUserCredentials rentacarUserCredentials = null;
        if (usernameOrEmail.isEmail())
        {
            rentacarUserCredentials = userCredentialsRepository.findByEmail(usernameOrEmail.getValue());
        }
        else
        {
            rentacarUserCredentials = userCredentialsRepository.findByUsername(usernameOrEmail.getValue());
        }

        if (rentacarUserCredentials == null)
        {
            throw new UsernameNotFoundException("User does not exist!");
        }
        if (rentacarUserCredentials.isLocked())
        {
            throw new UserLockedException();
        }
        if (!rentacarUserCredentials.isVerified())
        {
            throw new UserNotVerifiedException();
        }

        RentaCarUserProfile rentaCarUserProfile = profileRepository.findByCredentialsId(rentacarUserCredentials.getId());
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return new AuthenticatedUser(rentacarUserCredentials.getId(),
                rentacarUserCredentials.getUsername(),
                rentacarUserCredentials.getPassword(),
                rentaCarUserProfile.getId(),
                getClientIp(httpServletRequest),
                rentacarUserCredentials.getAuthorities().stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList());
    }

    private String getClientIp(HttpServletRequest request)
    {
        String remoteAddr = "";

        if (request != null)
        {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr))
            {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}
