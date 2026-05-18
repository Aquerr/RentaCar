package io.github.aquerr.rentacar.application.security.challengetoken.dto;

import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ChallengeToken(
        Long id,
        Long userId,
        OperationType operationType,
        String token,
        OffsetDateTime createdDate,
        OffsetDateTime expirationDate,
        boolean used)
{

}
