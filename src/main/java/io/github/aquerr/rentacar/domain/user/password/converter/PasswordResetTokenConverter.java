package io.github.aquerr.rentacar.domain.user.password.converter;

import io.github.aquerr.rentacar.domain.user.password.dto.PasswordResetTokenDto;
import io.github.aquerr.rentacar.domain.user.password.model.PasswordResetTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenConverter
{
    public PasswordResetTokenDto toDto(PasswordResetTokenEntity passwordResetTokenEntity)
    {
        if (passwordResetTokenEntity == null)
        {
            return null;
        }

        return PasswordResetTokenDto.builder()
                .id(passwordResetTokenEntity.getId())
                .credentialsId(passwordResetTokenEntity.getCredentialsId())
                .token(passwordResetTokenEntity.getToken())
                .used(passwordResetTokenEntity.isUsed())
                .expirationDate(passwordResetTokenEntity.getExpirationDate())
                .build();
    }
}
