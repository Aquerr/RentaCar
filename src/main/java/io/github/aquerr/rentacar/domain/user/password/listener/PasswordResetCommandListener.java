package io.github.aquerr.rentacar.domain.user.password.listener;

import io.github.aquerr.rentacar.domain.user.PasswordResetService;
import io.github.aquerr.rentacar.domain.user.password.PasswordResetCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class PasswordResetCommandListener
{
    private final PasswordResetService passwordResetService;

    @RabbitListener(queues = "account.password.reset.request")
    public void process(PasswordResetCommand command)
    {
        log.info("Processing command: {}", command);

        passwordResetService.resetPassword(command.getEmail());

        this.passwordResetService.send();
    }
}
