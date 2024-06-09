package io.github.aquerr.rentacar.domain.activation;

import io.github.aquerr.rentacar.application.exception.MessageSendException;
import io.github.aquerr.rentacar.application.lang.LangCode;
import io.github.aquerr.rentacar.application.rabbit.Event;
import io.github.aquerr.rentacar.application.rabbit.RabbitMessageSender;
import io.github.aquerr.rentacar.application.security.challengetoken.ChallengeTokenGenerator;
import io.github.aquerr.rentacar.application.security.challengetoken.dto.ChallengeToken;
import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import io.github.aquerr.rentacar.domain.activation.command.AccountActivationTokenRequestCommand;
import io.github.aquerr.rentacar.application.security.challengetoken.converter.ChallengeTokenConverter;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenAlreadyUsedException;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenExpiredException;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenNotFoundException;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.repository.ChallengeTokenRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountActivationService
{
    private final ChallengeTokenGenerator challengeTokenGenerator;
    private final ChallengeTokenRepository challengeTokenRepository;
    private final ChallengeTokenConverter challengeTokenConverter;
    private final RabbitMessageSender rabbitMessageSender;
    private final UserCredentialsRepository userCredentialsRepository;

    @Value("${rentacar.security.account.activation-token.expiration-time}")
    private Duration activationTokenExpirationTime;

    @Transactional
    public ChallengeTokenEntity invalidateOldActivationTokensAndGenerateNew(long userId)
    {
        this.challengeTokenRepository.invalidateOldChallengeTokens(userId);

        ChallengeTokenEntity challengeTokenEntity = ChallengeTokenEntity.builder()
                .userId(userId)
                .expirationDate(ZonedDateTime.now().plus(activationTokenExpirationTime))
                .token(this.challengeTokenGenerator.generate())
                .used(false)
                .build();

        this.challengeTokenRepository.save(challengeTokenEntity);

        return challengeTokenEntity;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ChallengeToken getActivationToken(String token)
    {
        return this.challengeTokenRepository.findByTokenAndOperationType(token, OperationType.ACTIVATION)
                .map(this.challengeTokenConverter::toDto)
                .orElseThrow(ActivationTokenNotFoundException::new);
    }

    @Transactional
    public void activate(ChallengeToken challengeToken)
    {
        if (challengeToken.used())
            throw new ActivationTokenAlreadyUsedException();
        if (challengeToken.expirationDate().isBefore(ZonedDateTime.now()))
            throw new ActivationTokenExpiredException();

        ChallengeTokenEntity challengeTokenEntity = this.challengeTokenRepository.findById(challengeToken.id())
                .orElseThrow(ActivationTokenNotFoundException::new)
                .toBuilder()
                .used(true)
                .build();

        this.challengeTokenRepository.save(challengeTokenEntity);

        UserCredentialsEntity credentials = userCredentialsRepository.findById(challengeTokenEntity.userId())
                .orElse(null);
        if (credentials != null)
        {
            credentials.setActivated(true);
            userCredentialsRepository.save(credentials);
        }
    }

    public void requestActivationToken(Long userId, String email, LangCode preferredLangCode)
    {
        Event event = new AccountActivationTokenRequestCommand(userId, email, preferredLangCode);
        try
        {
            this.rabbitMessageSender.send("account.activation.request", new AccountActivationTokenRequestCommand(userId, email, preferredLangCode));
        }
        catch (MessageSendException e)
        {
            log.error("Could not send message: {}", event, e);
        }
    }
}
