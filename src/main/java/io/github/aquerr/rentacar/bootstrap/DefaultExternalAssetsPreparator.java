package io.github.aquerr.rentacar.bootstrap;

import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.model.ImageKindFolder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

@Component
public class DefaultExternalAssetsPreparator implements ResourceLoaderAware
{
    private ResourceLoader resourceLoader;

    @EventListener(ApplicationStartedEvent.class)
    public void prepareExternalAssets()
    {
        try
        {
            File defaultAssetsResourceFolder = resourceLoader.getResource("classpath:default-assets").getFile();
            FileSystemUtils.copyRecursively(defaultAssetsResourceFolder.toPath().resolve("users"), ImageService.IMAGE_DIR_PATH.resolve(ImageKindFolder.USERS_PATH.getFolderName()));
            FileSystemUtils.copyRecursively(defaultAssetsResourceFolder.toPath().resolve("vehicles"), ImageService.IMAGE_DIR_PATH.resolve(ImageKindFolder.VEHICLES_PATH.getFolderName()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
}
