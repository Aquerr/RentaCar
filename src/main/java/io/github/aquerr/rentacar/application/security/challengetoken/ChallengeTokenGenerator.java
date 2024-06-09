package io.github.aquerr.rentacar.application.security.challengetoken;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ChallengeTokenGenerator
{
    public static final int CHALLENGE_TOKEN_LENGTH = 64;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public String generate()
    {
        return RandomStringUtils.random(CHALLENGE_TOKEN_LENGTH, 0, 0, true, true, null, SECURE_RANDOM);
    }
}
