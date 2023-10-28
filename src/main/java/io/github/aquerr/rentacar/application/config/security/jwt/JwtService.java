package io.github.aquerr.rentacar.application.config.security.jwt;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.InvalidJwtTokenEntity;
import io.github.aquerr.rentacar.repository.InvalidJwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public final class JwtService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final Map<String, BlackListedJwt> BLACK_LISTED_JWTS = new HashMap<>();
    private static ScheduledExecutorService BLACK_LISTED_JWTS_CLEARER_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    static {
        BLACK_LISTED_JWTS_CLEARER_EXECUTOR_SERVICE.scheduleAtFixedRate(JwtService::clearBlacklistedJwts, 1, 1, TimeUnit.HOURS);
    }
    private static void clearBlacklistedJwts()
    {
        for (final BlackListedJwt blackListedJwt : List.copyOf(BLACK_LISTED_JWTS.values()))
        {
            if (ZonedDateTime.now().isAfter(blackListedJwt.getExpirationTime()))
            {
                BLACK_LISTED_JWTS.remove(blackListedJwt.getJwt());
            }
        }
    }

    private final InvalidJwtTokenRepository invalidJwtTokenRepository;

    @Value("${rentacar.security.issuer}")
    private String jwtIssuer;
    @Value("${rentacar.security.jwt.expiration-time}")
    private Duration jwtExpirationTime;
    @Value("${rentacar.security.jwt.longer-expiration-time}")
    private Duration jwtLongerExpirationTime;

    @PostConstruct
    void initBlacklistedJwts()
    {
        invalidJwtTokenRepository.findAll().stream()
                .map(invalidJwtTokenEntity -> BlackListedJwt.of(invalidJwtTokenEntity.getJwt(), invalidJwtTokenEntity.getExpirationDateTime()))
                .forEach(blackListedJwt -> BLACK_LISTED_JWTS.put(blackListedJwt.getJwt(), blackListedJwt));
    }

    public String createJwt(String username, boolean rememberMe, Set<String> authorities) {
        authorities = Set.copyOf(authorities);
        Claims claims = Jwts.claims()
                .setSubject(username)
                .setIssuer(jwtIssuer)
                .setNotBefore(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(jwtExpirationTime)))
                .setIssuedAt(Date.from(Instant.now()));

        setExpirationTime(claims, rememberMe);

        claims.put("authorities", authorities);

        return Jwts.builder()
                .setSubject(username)
                .signWith(KEY)
                .compressWith(CompressionCodecs.DEFLATE)
                .setClaims(claims)
                .compact();
    }

    private void setExpirationTime(Claims claims, boolean rememberMe)
    {
        if (rememberMe) {
            claims.setExpiration(Date.from(Instant.now().plus(jwtLongerExpirationTime)));
        } else {
            claims.setExpiration(Date.from(Instant.now().plus(jwtExpirationTime)));
        }
    }

    public Jws<Claims> validateJwt(String jwt) {
        if (BLACK_LISTED_JWTS.containsKey(jwt))
            throw new IllegalStateException();

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .requireIssuer(jwtIssuer)
                    .build()
                    .parseClaimsJws(jwt);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    public String getJwtToken(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(AUTHORIZATION_HEADER))
                .filter(header -> header.startsWith(BEARER))
                .map(header -> header.substring(BEARER.length()))
                .orElse(null);
    }

    public void invalidate(String jwt) {
        Jws<Claims> jws = validateJwt(jwt);
        Date exprationDate = jws.getBody().getExpiration();
        ZonedDateTime expirationDateTime = exprationDate.toInstant().atZone(ZoneId.systemDefault());
        BLACK_LISTED_JWTS.put(jwt, BlackListedJwt.of(jwt, expirationDateTime));

        InvalidJwtTokenEntity invalidJwtTokenEntity = new InvalidJwtTokenEntity();
        try
        {
            invalidJwtTokenEntity.setJwt(jwt);
            invalidJwtTokenEntity.setInvalidatedDateTime(ZonedDateTime.now());
            invalidJwtTokenEntity.setExpirationDateTime(expirationDateTime);
            invalidJwtTokenRepository.save(invalidJwtTokenEntity);
        }
        catch (Exception exception)
        {
            log.error("Could not save invalid jwt token entity = " + invalidJwtTokenEntity, exception);
        }
    }

    @lombok.Value(staticConstructor = "of")
    private static class BlackListedJwt {
        String jwt;
        ZonedDateTime expirationTime;
    }
}