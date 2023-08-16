package io.github.aquerr.rentacar.bootstrap;

import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.model.ImageKindFolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
@Slf4j
public class DefaultExternalAssetsPreparator
{
    @EventListener(ApplicationStartedEvent.class)
    public void prepareExternalAssets()
    {
        try
        {
            prepareDefaultImages();
        }
        catch (IOException | URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void prepareDefaultImages() throws IOException, URISyntaxException
    {
        moveResources("classpath:default-assets/users/*.*", ImageService.IMAGE_DIR_PATH.resolve(ImageKindFolder.USERS_PATH.getFolderName()));
        moveResources("classpath:default-assets/vehicles/*.*", ImageService.IMAGE_DIR_PATH.resolve(ImageKindFolder.VEHICLES_PATH.getFolderName()));
    }

    private void moveResources(String sourceResourceDirectory, Path destinationDirectory) throws IOException
    {
        Files.createDirectories(destinationDirectory);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(sourceResourceDirectory);
        log.info("Copying default resources {} to {}", resources, destinationDirectory);
        for (final Resource resource : resources)
        {
            Files.copy(resource.getInputStream(), destinationDirectory.resolve(resource.getFilename()), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
