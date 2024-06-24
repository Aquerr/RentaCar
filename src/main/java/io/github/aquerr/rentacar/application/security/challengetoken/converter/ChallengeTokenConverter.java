package io.github.aquerr.rentacar.application.security.challengetoken.converter;

import io.github.aquerr.rentacar.application.security.challengetoken.dto.ChallengeToken;
import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class ChallengeTokenConverter
{
    public ChallengeToken toDto(ChallengeTokenEntity challengeTokenEntity)
    {
        if (challengeTokenEntity == null)
        {
            return null;
        }

        return ChallengeToken.builder()
                .id(challengeTokenEntity.getId())
                .userId(challengeTokenEntity.getUserId())
                .token(challengeTokenEntity.getToken())
                .used(challengeTokenEntity.isUsed())
                .expirationDate(challengeTokenEntity.getExpirationDate())
                .build();
    }
}
