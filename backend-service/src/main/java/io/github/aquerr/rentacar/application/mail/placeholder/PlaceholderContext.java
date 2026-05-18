package io.github.aquerr.rentacar.application.mail.placeholder;

import lombok.Value;

import java.util.Map;

@Value
public class PlaceholderContext
{
    String frontEndUrl;
    Map<String, String> properties;

    public String getProperty(String key)
    {
        return properties.get(key);
    }
}
