package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.exception.UserLockedException;
import io.github.aquerr.rentacar.application.exception.UserNotActivatedException;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.repository.UserRepository;
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
    private final UserRepository userRepository;

    public AuthenticatedUser loadById(Long id) throws UsernameNotFoundException
    {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return toAuthenticatedUser(userEntity);
    }

    @Override
    @Transactional
    public AuthenticatedUser loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserCredentials.UsernameOrEmail usernameOrEmail = new UserCredentials.UsernameOrEmail(username);

        UserEntity userEntity;
        if (usernameOrEmail.isEmail())
        {
            userEntity = userRepository.findByCredentials_Email(usernameOrEmail.getValue());
        }
        else
        {
            userEntity = userRepository.findByCredentials_Username(usernameOrEmail.getValue());
        }

        return toAuthenticatedUser(userEntity);
    }

    private AuthenticatedUser toAuthenticatedUser(UserEntity userEntity)
    {
        if (userEntity == null)
        {
            throw new UsernameNotFoundException("User does not exist!");
        }
        if (userEntity.getCredentials().isLocked())
        {
            throw new UserLockedException();
        }
        if (!userEntity.getCredentials().isActivated())
        {
            throw new UserNotActivatedException();
        }

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return new AuthenticatedUser(userEntity.getId(),
                userEntity.getCredentials().getUsername(),
                userEntity.getCredentials().getPassword(),
                getClientIp(httpServletRequest),
                userEntity.getAuthorities().stream()
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
