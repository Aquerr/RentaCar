package io.github.aquerr.rentacar.application.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("securityManager")
public final class SecurityManager
{
    public boolean canEditProfile(Authentication authentication, long profileId)
    {
        return isUserProfile(toAuthenticatedUser(authentication), profileId);
    }

    public boolean canEditUserData(Authentication authentication, long userId)
    {
        return isUser(toAuthenticatedUser(authentication), userId);
    }

    private boolean isUser(AuthenticatedUser authenticatedUser, long userId)
    {
        return authenticatedUser.getId() == userId;
    }

    private boolean isUserProfile(AuthenticatedUser authenticatedUser, long profileId)
    {
        return authenticatedUser.getProfileId() == profileId;
    }

    private AuthenticatedUser toAuthenticatedUser(Authentication authentication)
    {
        return (AuthenticatedUser) authentication.getPrincipal();
    }
}
