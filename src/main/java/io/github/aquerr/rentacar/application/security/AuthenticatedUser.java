package io.github.aquerr.rentacar.application.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class AuthenticatedUser implements UserDetails
{
    private final Long id;
    private final String username;
    private final String password;
    private final Long profileId;
    private final String remoteIpAddress;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.unmodifiableCollection(authorities);
    }

    public Long getId()
    {
        return id;
    }

    public Long getProfileId()
    {
        return profileId;
    }

    public String getRemoteIpAddress()
    {
        return remoteIpAddress;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
