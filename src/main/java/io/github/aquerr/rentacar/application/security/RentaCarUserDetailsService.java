package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.domain.user.model.RentaCarUser;
import io.github.aquerr.rentacar.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        RentaCarUser rentacarUser = userRepository.findByUsername(username);
        if (rentacarUser == null)
        {
            throw new UsernameNotFoundException("User does not exist!");
        }
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return new AuthenticatedUser(rentacarUser.id(), rentacarUser.username(), rentacarUser.password(), getClientIp(httpServletRequest), rentacarUser.authorities().stream()
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
