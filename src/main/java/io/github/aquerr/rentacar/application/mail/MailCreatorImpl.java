package io.github.aquerr.rentacar.application.mail;

import io.github.aquerr.rentacar.application.lang.LangCode;
import io.github.aquerr.rentacar.application.mail.exception.CouldNotGenerateMailException;
import io.github.aquerr.rentacar.application.mail.placeholder.CommonPlaceholders;
import io.github.aquerr.rentacar.application.mail.placeholder.PlaceholderContext;
import io.github.aquerr.rentacar.application.mail.placeholder.PlaceholderValueResolver;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class MailCreatorImpl implements MailCreator
{
    private final JavaMailSender javaMailSender;
    private final MessageSource mailMessagesMessageSource;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${rentacar.front-end-url}")
    private String frontEndUrlBaseUrl;

    private final Map<MailType, Map<String, PlaceholderValueResolver>> MAIL_PLACEHOLDER_POPULATORS = Map.of(
            MailType.ACCOUNT_ACTIVATION, Map.of(
                    CommonPlaceholders.REDIRECT_URL, (placeholderContext ->
                            placeholderContext.getFrontEndUrl() + "/activation-account?token=" + placeholderContext.getProperty(CommonPlaceholders.TOKEN))),
            MailType.PASSWORD_RESET, Map.of(
                    CommonPlaceholders.REDIRECT_URL, (placeholderContext ->
                            placeholderContext.getFrontEndUrl() + "/password-reset?token=" + placeholderContext.getProperty(CommonPlaceholders.TOKEN))
            )
    );

    @Override
    public MimeMessage create(MailMessageProperties message) throws CouldNotGenerateMailException
    {
        try
        {
            MimeMessageHelper messageHelper = getMimeMessageHelper(false, message);
            String mailTemplate = getMailTemplate(message.getType(), message.getLangCode());

            PlaceholderContext placeholderContext = new PlaceholderContext(frontEndUrlBaseUrl, message.getAdditionalProperties());

            for (Map.Entry<String, PlaceholderValueResolver> entry : MAIL_PLACEHOLDER_POPULATORS.get(message.getType()).entrySet())
            {
                mailTemplate = mailTemplate.replaceAll(buildPlaceholder(entry.getKey()), entry.getValue().resolve(placeholderContext));
            }

            messageHelper.setText(mailTemplate, true);
            return messageHelper.getMimeMessage();
        }
        catch (Exception exception)
        {
            throw new CouldNotGenerateMailException(exception);
        }
    }

    private String buildPlaceholder(String placeholderName)
    {
        return "{" + placeholderName + "}";
    }

    private MimeMessageHelper getMimeMessageHelper(boolean multipart) throws MessagingException
    {
        return new MimeMessageHelper(javaMailSender.createMimeMessage(), multipart, StandardCharsets.UTF_8.displayName());
    }

    private MimeMessageHelper getMimeMessageHelper(boolean multipart, MailMessageProperties message) throws MessagingException
    {
        MimeMessageHelper messageHelper = getMimeMessageHelper(multipart);
        messageHelper.setFrom(mailFrom);
        messageHelper.setTo(message.getTo());
        messageHelper.setSubject(resolveMessageSubject(message.getType(), message.getLangCode()));
        return messageHelper;
    }

    private String resolveMessageSubject(MailType mailType, LangCode langCode)
    {
        return mailMessagesMessageSource.getMessage("mail.subject." + mailType.name(), null, Locale.forLanguageTag(langCode.getCode()));
    }

    private String getMailTemplate(MailType mailType, LangCode langCode) throws IOException
    {
        return Files.readString(resolveMailTemplatePath(mailType, langCode), StandardCharsets.UTF_8);
    }

    private Path resolveMailTemplatePath(MailType mailType, LangCode langCode) throws IOException
    {
        return new ClassPathResource(MessageFormat.format("/templates/{0}/{1}.html", langCode.getCode(), mailType.name().toLowerCase())).getFile().toPath();
    }
}
