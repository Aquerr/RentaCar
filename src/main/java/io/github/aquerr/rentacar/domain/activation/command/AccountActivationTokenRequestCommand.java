package io.github.aquerr.rentacar.domain.activation.command;

import io.github.aquerr.rentacar.application.lang.LangCode;
import io.github.aquerr.rentacar.application.rabbit.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountActivationTokenRequestCommand implements Event
{
    private Long credentialsId;
    private String emailTo;
    private LangCode langCode;
}
