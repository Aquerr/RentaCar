package io.github.aquerr.rentacar.domain.activation;

import io.github.aquerr.rentacar.application.exception.MessageSendException;
import io.github.aquerr.rentacar.application.lang.LangCode;
import io.github.aquerr.rentacar.application.rabbit.Event;
import io.github.aquerr.rentacar.application.rabbit.RabbitMessageSender;
import io.github.aquerr.rentacar.application.security.AccessTokenGenerator;
import io.github.aquerr.rentacar.domain.activation.command.AccountActivationTokenRequestCommand;
import io.github.aquerr.rentacar.domain.activation.converter.ActivationTokenConverter;
import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenDto;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenAlreadyUsedException;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenExpiredException;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenNotFoundException;
import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.repository.ActivationTokenRepository;
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
    private final AccessTokenGenerator accessTokenGenerator;
    private final ActivationTokenRepository activationTokenRepository;
    private final ActivationTokenConverter activationTokenConverter;
    private final RabbitMessageSender rabbitMessageSender;
    private final UserCredentialsRepository userCredentialsRepository;

    @Value("${rentacar.security.account.activation-token.expiration-time}")
    private Duration activationTokenExpirationTime;

    @Transactional
    public ActivationTokenEntity invalidateOldActivationTokensAndGenerateNew(long userId)
    {
        this.activationTokenRepository.invalidateOldActivationTokens(userId);

        ActivationTokenEntity activationTokenEntity = new ActivationTokenEntity();
        activationTokenEntity.setUserId(userId);
        activationTokenEntity.setExpirationDate(ZonedDateTime.now().plus(activationTokenExpirationTime));
        activationTokenEntity.setToken(this.accessTokenGenerator.generate());
        activationTokenEntity.setUsed(false);
        this.activationTokenRepository.save(activationTokenEntity);

        return activationTokenEntity;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ActivationTokenDto getActivationToken(String token)
    {
        return this.activationTokenRepository.findByToken(token).map(this.activationTokenConverter::toDto)
                .orElseThrow(ActivationTokenNotFoundException::new);
    }

    @Transactional
    public void activate(ActivationTokenDto activationTokenDto)
    {
        if (activationTokenDto.isUsed())
            throw new ActivationTokenAlreadyUsedException();
        if (activationTokenDto.getExpirationDate().isBefore(ZonedDateTime.now()))
            throw new ActivationTokenExpiredException();

        ActivationTokenEntity activationTokenEntity = this.activationTokenRepository.findById(activationTokenDto.getId())
                .orElseThrow(ActivationTokenNotFoundException::new);
        activationTokenEntity.setUsed(true);
        this.activationTokenRepository.save(activationTokenEntity);

        UserCredentialsEntity credentials = userCredentialsRepository.findById(activationTokenDto.getUserId())
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
