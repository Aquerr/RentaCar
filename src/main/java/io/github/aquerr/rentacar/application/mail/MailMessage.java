package io.github.aquerr.rentacar.application.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailMessage
{
    private String to;
    private String subject;
    private MailType type;
    private Map<String, String> properties;
}
