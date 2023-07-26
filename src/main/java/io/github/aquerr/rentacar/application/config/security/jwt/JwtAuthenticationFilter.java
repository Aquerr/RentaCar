package io.github.aquerr.rentacar.application.config.security.jwt;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.RentaCarUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

    private final JwtService jwtService;
    private final RentaCarUserDetailsService rentaCarUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String jwt = jwtService.getJwtToken(request);
        if (jwt == null)
        {
            filterChain.doFilter(request, response);
            return;
        }
        try
        {
            log.debug("Validating JWT: " + jwt);
            Jws<Claims> claimsJws = jwtService.validateJwt(jwt);
            if (claimsJws != null)
            {
                authenticate(claimsJws, request);
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception exception)
        {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private void authenticate(Jws<Claims> claimsJws, HttpServletRequest request)
    {
        String username = claimsJws.getBody().getSubject();
        AuthenticatedUser authenticatedUser = rentaCarUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticatedUser, authenticatedUser.getPassword(), authenticatedUser.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
