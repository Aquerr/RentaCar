package io.github.aquerr.rentacar.application.security.challengetoken.dto;

import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ChallengeToken(
        Long id,
        Long userId,
        OperationType operationType,
        String token,
        ZonedDateTime createdDate,
        ZonedDateTime expirationDate,
        boolean used)
{

}
