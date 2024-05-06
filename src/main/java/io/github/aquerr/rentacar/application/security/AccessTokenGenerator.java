package io.github.aquerr.rentacar.application.security;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccessTokenGenerator
{
    public static final int ACTIVATION_TOKEN_LENGTH = 64;
    public static final int PASSWORD_RESET_TOKEN_LENGTH = 64;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public String generate()
    {
        return RandomStringUtils.random(ACTIVATION_TOKEN_LENGTH, 0, 0, true, true, null, SECURE_RANDOM);
    }
}
