package io.github.aquerr.rentacar.workflow.rentacar.domain.activation.command;

import io.github.aquerr.rentacar.workflow.rentacar.application.lang.LangCode;
import io.github.aquerr.rentacar.workflow.rentacar.application.rabbit.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountActivationTokenRequestCommand implements Event
{
    private Long userId;
    private String emailTo;
    private LangCode langCode;
}
