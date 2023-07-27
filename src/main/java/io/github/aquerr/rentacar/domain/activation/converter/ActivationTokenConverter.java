package io.github.aquerr.rentacar.domain.activation.converter;

import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenDto;
import io.github.aquerr.rentacar.domain.activation.model.ActivationToken;
import org.springframework.stereotype.Component;

@Component
public class ActivationTokenConverter
{
    public ActivationTokenDto toDto(ActivationToken activationToken)
    {
        if (activationToken == null)
        {
            return null;
        }

        return ActivationTokenDto.builder()
                .id(activationToken.getId())
                .credentialsId(activationToken.getCredentialsId())
                .token(activationToken.getToken())
                .used(activationToken.isUsed())
                .expirationDate(activationToken.getExpirationDate())
                .build();
    }
}
