package io.github.aquerr.rentacar.application.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Value
public class UserCredentials
{
    UsernameOrEmail login;
    String password;
    boolean rememberMe;

    @JsonCreator
    public UserCredentials(@JsonProperty("login") UsernameOrEmail usernameOrEmail,
                           @JsonProperty("password") String password,
                           @JsonProperty("rememberMe") boolean rememberMe)
    {
        this.login = usernameOrEmail;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    @Value
    public static class UsernameOrEmail
    {
        private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        String login;
        @JsonIgnore
        boolean isEmail;

        public UsernameOrEmail(String login)
        {
            this.isEmail = EMAIL_PATTERN.matcher(login).matches();
            this.login = login;
        }
        public boolean isEmail()
        {
            return this.isEmail;
        }

        public String getValue()
        {
            return login;
        }

        @Component
        public static class UsernameOrEmailConverter implements Converter<String, UsernameOrEmail>
        {
            @Override
            public UsernameOrEmail convert(String source)
            {
                return new UsernameOrEmail(source);
            }
        }
    }
}