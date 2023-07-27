package io.github.aquerr.rentacar.domain.activation;

import io.github.aquerr.rentacar.application.security.AccessTokenGenerator;
import io.github.aquerr.rentacar.domain.activation.converter.ActivationTokenConverter;
import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenDto;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenAlreadyUsedException;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenExpiredException;
import io.github.aquerr.rentacar.domain.activation.exception.ActivationTokenNotFoundException;
import io.github.aquerr.rentacar.domain.activation.model.ActivationToken;
import io.github.aquerr.rentacar.domain.user.model.RentaCarUserCredentials;
import io.github.aquerr.rentacar.repository.ActivationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class AccountActivationService
{
    private final AccessTokenGenerator accessTokenGenerator;
    private final ActivationTokenRepository activationTokenRepository;
    private final ActivationTokenConverter activationTokenConverter;

    @Value("${rentacar.security.account.activation-token.expiration-time}")
    private Duration activationTokenExpirationTime;

    @Transactional
    public ActivationTokenDto requestActivationToken(RentaCarUserCredentials credentials)
    {
        ActivationToken activationToken = new ActivationToken();
        activationToken.setCredentialsId(credentials.getId());
        activationToken.setExpirationDate(ZonedDateTime.now().plus(activationTokenExpirationTime));
        activationToken.setToken(this.accessTokenGenerator.generate());
        activationToken.setUsed(false);
        this.activationTokenRepository.save(activationToken);
        return this.activationTokenConverter.toDto(activationToken);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ActivationTokenDto getActivationToken(String token)
    {
        return this.activationTokenRepository.findByToken(token).map(this.activationTokenConverter::toDto)
                .orElseThrow(ActivationTokenNotFoundException::new);
    }

    @Transactional
    public void activateToken(ActivationTokenDto activationTokenDto)
    {
        if (activationTokenDto.isUsed())
            throw new ActivationTokenAlreadyUsedException();
        if (activationTokenDto.getExpirationDate().isBefore(ZonedDateTime.now()))
            throw new ActivationTokenExpiredException();

        ActivationToken activationToken = this.activationTokenRepository.findById(activationTokenDto.getId())
                .orElseThrow(ActivationTokenNotFoundException::new);
        activationToken.setUsed(true);
        this.activationTokenRepository.save(activationToken);
    }
}
