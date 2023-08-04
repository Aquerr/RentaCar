package io.github.aquerr.rentacar.domain.activation.command;

import io.github.aquerr.rentacar.application.lang.LangCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountActivationTokenRequestCommand
{
    private Long credentialsId;
    private String emailTo;
    private LangCode langCode;
}
