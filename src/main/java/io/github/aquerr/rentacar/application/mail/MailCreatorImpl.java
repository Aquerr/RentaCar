package io.github.aquerr.rentacar.application.mail;

import io.github.aquerr.rentacar.application.lang.LangCode;
import io.github.aquerr.rentacar.application.mail.exception.CouldNotGenerateMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
public class MailCreatorImpl implements MailCreator
{
    private final JavaMailSender javaMailSender;

    private final Map<MailType, Supplier<MailMessageCreator>> mailCreators = Map.of(
            MailType.ACCOUNT_ACTIVATION, this::accountActivationMailCreator,
            MailType.RENTAL_CONFIRMATION, this::rentalConfirmationMailCreator
    );

    private final Map<MailType, String> mailTemplates = Map.of(
            MailType.ACCOUNT_ACTIVATION, "/templates/{langCode}/account_activation.html"
    );

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${rentacar.front-end-url}")
    private String frontEndUrl;

    @Override
    public MimeMessage create(MailMessage message) throws CouldNotGenerateMailException
    {
        try
        {
            return this.mailCreators.get(message.getType()).get().build(message);
        }
        catch (Exception exception)
        {
            throw new CouldNotGenerateMailException(exception);
        }
    }

    private MailMessageCreator rentalConfirmationMailCreator()
    {
        return message ->
        {
            throw new UnsupportedOperationException("rentalConfirmationMailCreator not yet implemented!");
        };
    }

    private MailMessageCreator accountActivationMailCreator()
    {
        return message ->
        {
            MimeMessageHelper messageHelper = getMimeMessageHelper(false);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(message.getTo());
            messageHelper.setSubject(message.getSubject());
            String mailTemplate = getMailTemplate(message.getType(), message.getLangCode());
            String token = message.getProperties().get("activation_token");
            String activationLinkUrl = frontEndUrl + "/activation-account?token=" + token;
            mailTemplate = mailTemplate.replace("${activation_link}", activationLinkUrl);
            messageHelper.setText(mailTemplate, true);
            return messageHelper.getMimeMessage();
        };
    }

    private MimeMessageHelper getMimeMessageHelper(boolean multipart) throws MessagingException
    {
        return new MimeMessageHelper(javaMailSender.createMimeMessage(), multipart, StandardCharsets.UTF_8.displayName());
    }

    private String getMailTemplate(MailType mailType, LangCode langCode) throws IOException
    {
        return Files.readString(new ClassPathResource(mailTemplates.get(mailType).replace("{langCode}", langCode.getCode())).getFile().toPath(), StandardCharsets.UTF_8);
    }
}
