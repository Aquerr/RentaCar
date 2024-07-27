package io.github.aquerr.rentacar.application.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Configuration
public class CacheConfig
{
    public static final String FAILED_LOGIN_ACCOUNTS = "failed_login_accounts";
    public static final String FAILED_LOGIN_ACCOUNTS_FROM_IPS = "failed_login_accounts_from_ips";

    @Value("${login-restrictions.failed-login-attempts-cache-ttl}")
    private Duration failedLoginAccountsTtl;

    @Value("${login-restrictions.failed-login-attempts-from-ips-cache-ttl}")
    private Duration failedLoginFromIps;

    @Bean
    public CacheManager cacheManager()
    {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(configureCaches());
        return simpleCacheManager;
    }

    private Collection<? extends Cache> configureCaches()
    {
        return List.of(
                failedLoginAccountsCache(),
                failedLoginAccountsFromIpsCache()
        );
    }

    private Cache failedLoginAccountsCache()
    {
        return new CaffeineCache(FAILED_LOGIN_ACCOUNTS, Caffeine.newBuilder()
                .expireAfterWrite(failedLoginAccountsTtl)
                .build());
    }

    private Cache failedLoginAccountsFromIpsCache()
    {
        return new CaffeineCache(FAILED_LOGIN_ACCOUNTS_FROM_IPS, Caffeine.newBuilder()
                .expireAfterWrite(failedLoginFromIps)
                .build());
    }
}
