package io.github.aquerr.rentacar.workflow.rentacar.domain.profile.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateProfileParameters
{
    Long userId;
    String email;
}
