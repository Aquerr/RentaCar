package io.github.aquerr.rentacar.domain.user.model;

import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import lombok.Data;

@Data
public class UserCredentialsEntityWithActivationTokenEntity
{
    UserCredentialsEntity userCredentials;
    ActivationTokenEntity activationTokenEntity;
}
