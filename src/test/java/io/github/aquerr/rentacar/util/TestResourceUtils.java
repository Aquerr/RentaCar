package io.github.aquerr.rentacar.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class TestResourceUtils
{
    private TestResourceUtils()
    {

    }

    public static String loadMockJson(String path) {
        ClassPathResource classPathResource = new ClassPathResource(path);
        try(InputStream resourceInputStream = classPathResource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceInputStream)))
        {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static byte[] loadImage(String path)
    {
        try
        {
            ClassPathResource classPathResource = new ClassPathResource(path);
            return classPathResource.getContentAsByteArray();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
