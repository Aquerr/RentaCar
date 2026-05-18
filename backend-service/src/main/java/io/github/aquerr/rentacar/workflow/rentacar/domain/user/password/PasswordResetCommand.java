package io.github.aquerr.rentacar.workflow.rentacar.domain.user.password;

import io.github.aquerr.rentacar.workflow.rentacar.application.rabbit.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetCommand implements Event
{
    private String email;
}
