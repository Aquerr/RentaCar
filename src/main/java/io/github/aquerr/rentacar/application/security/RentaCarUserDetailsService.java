package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.exception.UserLockedException;
import io.github.aquerr.rentacar.application.exception.UserNotActivatedException;
import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
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

    public AuthenticatedUser loadById(Long id) throws UsernameNotFoundException
    {
        UserCredentialsEntity userCredentialsEntity = userCredentialsRepository.findById(id).orElse(null);
        return toAuthenticatedUser(userCredentialsEntity);
    }

    @Override
    @Transactional
    public AuthenticatedUser loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserCredentials.UsernameOrEmail usernameOrEmail = new UserCredentials.UsernameOrEmail(username);

        UserCredentialsEntity userCredentialsEntity;
        if (usernameOrEmail.isEmail())
        {
            userCredentialsEntity = userCredentialsRepository.findByEmail(usernameOrEmail.getValue());
        }
        else
        {
            userCredentialsEntity = userCredentialsRepository.findByUsername(usernameOrEmail.getValue());
        }

        return toAuthenticatedUser(userCredentialsEntity);
    }

    private AuthenticatedUser toAuthenticatedUser(UserCredentialsEntity userCredentialsEntity)
    {
        if (userCredentialsEntity == null)
        {
            throw new UsernameNotFoundException("User does not exist!");
        }
        if (userCredentialsEntity.isLocked())
        {
            throw new UserLockedException();
        }
        if (!userCredentialsEntity.isActivated())
        {
            throw new UserNotActivatedException();
        }

        UserProfileEntity userProfileEntity = profileRepository.findByCredentialsId(userCredentialsEntity.getId());
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return new AuthenticatedUser(userCredentialsEntity.getId(),
                userCredentialsEntity.getUsername(),
                userCredentialsEntity.getPassword(),
                userProfileEntity.getId(),
                getClientIp(httpServletRequest),
                userCredentialsEntity.getAuthorities().stream()
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
