package io.github.aquerr.rentacar.application.lang;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum LangCode
{
    ENGLISH("en"),
    POLISH("pl");

    private final String code;

    LangCode(String code)
    {
        this.code = code;
    }

    @JsonValue
    public String getCode()
    {
        return code;
    }

    @JsonCreator
    public static LangCode forCode(String code)
    {
        return Arrays.stream(LangCode.values())
                .filter(code1 -> code1.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
