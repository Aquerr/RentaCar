package io.github.aquerr.rentacar.application.security.mfa.converter;

import io.github.aquerr.rentacar.application.security.mfa.dto.UserMfaSettings;
import io.github.aquerr.rentacar.application.security.mfa.model.UserMfaSettingsEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMfaSettingsConverter
{
    public UserMfaSettings convertToDto(UserMfaSettingsEntity entity)
    {
        if (entity == null)
        {
            return null;
        }

        UserMfaSettings userMfaSettings = new UserMfaSettings();
        userMfaSettings.setId(entity.getId());
        userMfaSettings.setCredentialsId(entity.getCredentialsId());
        userMfaSettings.setMfaType(entity.getMfaType());
        userMfaSettings.setVerified(entity.isVerified());
        userMfaSettings.setVerifiedDate(entity.getVerifiedDate());
        return userMfaSettings;
    }
}
