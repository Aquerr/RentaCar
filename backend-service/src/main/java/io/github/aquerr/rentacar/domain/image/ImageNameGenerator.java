package io.github.aquerr.rentacar.domain.image;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ImageNameGenerator
{
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    String generate()
    {
        return RandomStringUtils.random(20, 0, 0, true, true, null, SECURE_RANDOM);
    }
}
