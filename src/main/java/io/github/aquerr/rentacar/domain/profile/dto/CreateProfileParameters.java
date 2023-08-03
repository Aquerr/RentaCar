package io.github.aquerr.rentacar.domain.profile.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateProfileParameters
{
    Long credentialsId;
    String email;
}
