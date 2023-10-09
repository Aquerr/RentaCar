package io.github.aquerr.rentacar.domain.image;

import io.github.aquerr.rentacar.application.security.exception.AccessDeniedException;
import io.github.aquerr.rentacar.domain.image.dto.ImageUri;
import io.github.aquerr.rentacar.domain.image.exception.CouldNotDeleteImageException;
import io.github.aquerr.rentacar.domain.image.exception.CouldNotSaveImageException;
import io.github.aquerr.rentacar.domain.image.exception.ImageNotFoundException;
import io.github.aquerr.rentacar.domain.image.model.ImageKind;
import io.github.aquerr.rentacar.domain.image.model.ImageKindFolder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService
{
    public static final String API_IMAGE_URL = "/api/v1/assets/images";
    public static final Path IMAGE_DIR_PATH = Paths.get(".").resolve("assets").resolve("images");

    private final ImageNameGenerator imageNameGenerator;

    @Value("${rentacar.back-end-url}")
    private String backendUrl;

    @PostConstruct
    void initStorage()
    {
        try
        {
            Files.createDirectories(IMAGE_DIR_PATH);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ImageUri saveImage(MultipartFile multipartFile, ImageKind imageKind)
    {
        String fileExtension = getFileExtension(multipartFile);
        String imageName;
        do
        {
            imageName = imageNameGenerator.generate();
            imageName += fileExtension;
        }
        while (doesImageExists(imageKind, imageName));

        Path imagePath = prepareImagePath(imageKind, imageName);

        try
        {
            doSave(multipartFile.getInputStream(), imagePath);
        }
        catch (IOException e)
        {
            throw new CouldNotSaveImageException();
        }
        return ImageUri.of(prepareImageUrl(imageKind, imageName));
    }

    private void doSave(InputStream inputStream, Path imagePath) throws IOException
    {
        Files.createDirectories(imagePath.getParent());
        Files.copy(inputStream, imagePath);
    }

    public void deleteImage(ImageKind imageKind, String fileName)
    {
        try
        {
            Path imagePath = prepareImagePath(imageKind, fileName);

            // Sanity check, just in case something bad is in fileName.
            if (!imagePath.startsWith(IMAGE_DIR_PATH.resolve(ImageKindFolder.getByImageKind(imageKind).getFolderName())) || !Files.isRegularFile(imagePath))
            {
                throw new AccessDeniedException();
            }

            if (Files.notExists(imagePath))
            {
                throw new ImageNotFoundException(fileName);
            }

            Files.delete(imagePath);
        }
        catch (IOException e)
        {
            throw new CouldNotDeleteImageException();
        }
    }

    public byte[] getImageBytes(ImageKind imageKind, String fileName)
    {
        Path imagePath = prepareImagePath(imageKind, fileName);

        // Sanity check, just in case something bad is in fileName.
        if (!imagePath.startsWith(IMAGE_DIR_PATH.resolve(ImageKindFolder.getByImageKind(imageKind).getFolderName()).normalize()))
        {
            throw new AccessDeniedException();
        }
        else if (Files.notExists(imagePath))
        {
            throw new ImageNotFoundException(fileName);
        }

        try
        {
            return Files.readAllBytes(imagePath);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ImageUri getImageUri(ImageKind imageKind, String imageName)
    {
        return ImageUri.of(prepareImageUrl(imageKind, imageName));
    }

    public String retrieveImageName(String iconUrl)
    {
        if (ObjectUtils.isEmpty(iconUrl))
            return "";

        List<String> pathSegments = UriComponentsBuilder.fromUriString(iconUrl).build().getPathSegments();
        return pathSegments.get(pathSegments.size() - 1);
    }

    private boolean doesImageExists(ImageKind imageKind, String fileName)
    {
        return Files.exists(prepareImagePath(imageKind, fileName));
    }

    private Path prepareImagePath(ImageKind imageKind, String fileName)
    {
        ImageKindFolder imageKindFolder = ImageKindFolder.getByImageKind(imageKind);
        return IMAGE_DIR_PATH.resolve(imageKindFolder.getFolderName()).resolve(fileName).normalize();
    }

    private String getFileExtension(MultipartFile multipartFile)
    {
        return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
    }

    private URI prepareImageUrl(ImageKind imageKind, String imageName)
    {
        return UriComponentsBuilder.fromUriString(backendUrl + API_IMAGE_URL).pathSegment(imageKind.name()).pathSegment(imageName).build().toUri();
    }
}
