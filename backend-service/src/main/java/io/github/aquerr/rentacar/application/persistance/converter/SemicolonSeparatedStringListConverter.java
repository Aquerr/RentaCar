package io.github.aquerr.rentacar.application.persistance.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Converter
public class SemicolonSeparatedStringListConverter implements AttributeConverter<List<String>, String>
{
    private static final String SEPARATOR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> attribute)
    {
        return Optional.ofNullable(attribute)
                .map(attribute1 -> String.join(SEPARATOR, attribute1))
                .orElse("");
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData)
    {
        return Optional.ofNullable(dbData)
                .filter(data -> !ObjectUtils.isEmpty(data))
                .map(dbdata -> Arrays.stream(dbData.split(SEPARATOR)).toList())
                .orElse(Collections.emptyList());
    }
}
