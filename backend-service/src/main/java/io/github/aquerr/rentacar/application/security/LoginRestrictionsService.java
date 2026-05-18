package io.github.aquerr.rentacar.application.security;

import io.github.aquerr.rentacar.application.config.CacheConfig;
import io.github.aquerr.rentacar.application.config.security.LoginRestrictionsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginRestrictionsService
{
    private final CacheManager cacheManager;
    private final LoginRestrictionsProperties properties;

    public boolean canLogin(String username)
    {
        Cache accountCache = cacheManager.getCache(CacheConfig.FAILED_LOGIN_ACCOUNTS);

        int currentFailedLoginCount = getIntegerFromCache(accountCache, username);
        if (currentFailedLoginCount >= properties.getMaxFailedLoginAttempts())
            return false;

        return true;
    }

    public boolean canLoginFromIp(String ipAddress)
    {
        Cache ipCache = cacheManager.getCache(CacheConfig.FAILED_LOGIN_ACCOUNTS_FROM_IPS);
        int currentFailedLoginFromIpCount = getIntegerFromCache(ipCache, ipAddress);
        if (currentFailedLoginFromIpCount >= properties.getMaxFailedLoginAttempts())
            return false;
        return true;
    }

    public void incrementFailedLoginForUsername(String username)
    {
        Cache accountCache = cacheManager.getCache(CacheConfig.FAILED_LOGIN_ACCOUNTS);
        incrementIntegerValue(accountCache, username);
    }

    public void incrementFailedLoginForIpAddress(String ipAddress)
    {
        Cache ipCache = cacheManager.getCache(CacheConfig.FAILED_LOGIN_ACCOUNTS_FROM_IPS);
        incrementIntegerValue(ipCache, ipAddress);
    }

    private static int getIntegerFromCache(Cache cache, String key)
    {
        return Optional.ofNullable(cache.get(key, Integer.class))
                .orElse(0);
    }

    private static void incrementIntegerValue(Cache cache, String key)
    {
        int currentFailedLoginCount = Optional.ofNullable(cache.get(key, Integer.class))
                .orElse(0);
        cache.put(key, currentFailedLoginCount + 1);
    }
}
