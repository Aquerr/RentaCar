package io.github.aquerr.rentacar.application.security.challengetoken;

import io.github.aquerr.rentacar.application.security.challengetoken.dto.ChallengeToken;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import io.github.aquerr.rentacar.repository.ChallengeTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ChallengeTokenService
{
    private final ChallengeTokenRepository challengeTokenRepository;

    @Transactional
    public ChallengeToken find(String userId, String token)
    {

    }

    @Transactional
    public void delete(String userId, String token)
    {

    }

    @Transactional
    public ChallengeToken generateAndSave(String userId, OperationType operationType)
    {

    }
}
