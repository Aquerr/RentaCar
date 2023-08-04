package io.github.aquerr.rentacar.application.mail;

import io.github.aquerr.rentacar.application.lang.LangCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailMessage
{
    private String to;
    private String subject;
    private MailType type;
    private LangCode langCode;
    private Map<String, String> properties;
}
