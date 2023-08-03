package io.github.aquerr.rentacar.domain.activation.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountActivationTokenRequestCommand
{
    private Long credentialsId;
    private String email;
}
