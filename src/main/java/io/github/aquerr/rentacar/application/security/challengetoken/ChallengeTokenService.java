package io.github.aquerr.rentacar.application.security.challengetoken;

import io.github.aquerr.rentacar.application.security.challengetoken.converter.ChallengeTokenConverter;
import io.github.aquerr.rentacar.application.security.challengetoken.dto.ChallengeToken;
import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import io.github.aquerr.rentacar.repository.ChallengeTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ChallengeTokenService
{
    private final ChallengeTokenRepository challengeTokenRepository;
    private final ChallengeTokenConverter challengeTokenConverter;
    private final ChallengeTokenGenerator challengeTokenGenerator;

    @Transactional
    public ChallengeToken find(long userId, String token)
    {
        return this.challengeTokenRepository.findByUserIdAndToken(userId, token)
                .map(challengeTokenConverter::toDto)
                .orElse(null);
    }

    public ChallengeToken find(String token, OperationType operationType)
    {
        return this.challengeTokenRepository.findByTokenAndOperationType(token, operationType)
                .map(challengeTokenConverter::toDto)
                .orElse(null);
    }

    @Transactional
    public void delete(long userId, String token)
    {
        this.challengeTokenRepository.deleteByUserIdAndToken(userId, token);
    }

    @Transactional
    public void deleteForUsers(List<Long> userIds, OperationType operationType)
    {
        this.challengeTokenRepository.deleteAllByUserIdInAndOperationType(userIds, operationType);
    }

    @Transactional
    public ChallengeToken generateAndSave(long userId,
                                          OperationType operationType,
                                          Duration expirationTime)
    {
        ChallengeTokenEntity challengeTokenEntity = ChallengeTokenEntity.builder()
                .userId(userId)
                .operationType(operationType)
                .createdDate(OffsetDateTime.now())
                .expirationDate(OffsetDateTime.now().plus(expirationTime))
                .token(this.challengeTokenGenerator.generate())
                .used(false)
                .build();

        this.challengeTokenRepository.save(challengeTokenEntity);

        return challengeTokenConverter.toDto(challengeTokenEntity);
    }

    @Transactional
    public void invalidateOldChallengeTokens(long userId, OperationType operationType)
    {
        this.challengeTokenRepository.invalidateOldChallengeTokens(userId, operationType);
    }

    @Transactional
    public void markAsUsed(String token, OperationType operationType)
    {
        this.challengeTokenRepository.updateByTokenSetUsedTrue(token, operationType);
    }
}
