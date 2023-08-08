package io.github.aquerr.rentacar.domain.activation.converter;

import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenDto;
import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class ActivationTokenConverter
{
    public ActivationTokenDto toDto(ActivationTokenEntity activationTokenEntity)
    {
        if (activationTokenEntity == null)
        {
            return null;
        }

        return ActivationTokenDto.builder()
                .id(activationTokenEntity.getId())
                .credentialsId(activationTokenEntity.getCredentialsId())
                .token(activationTokenEntity.getToken())
                .used(activationTokenEntity.isUsed())
                .expirationDate(activationTokenEntity.getExpirationDate())
                .build();
    }
}
