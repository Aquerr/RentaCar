package io.github.aquerr.rentacar.application.config;

import io.github.aquerr.rentacar.application.mail.MailSender;
import io.github.aquerr.rentacar.application.mail.RentaCarMailSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig
{
    @Bean
    @ConditionalOnProperty(value = "rentacar.mail-sender.enabled", matchIfMissing = true, havingValue = "true")
    public MailSender rentaCarMailSender(JavaMailSender javaMailSender)
    {
        return new RentaCarMailSender(javaMailSender);
    }

    @Bean
    @ConditionalOnProperty(value = "rentacar.mail-sender.enabled", matchIfMissing = true, havingValue = "false")
    public MailSender noOpMailSender()
    {
        return new MailSender.NoOpMailSender();
    }
}
