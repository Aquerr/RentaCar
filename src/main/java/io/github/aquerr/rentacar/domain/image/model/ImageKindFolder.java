package io.github.aquerr.rentacar.domain.image.model;

import java.util.stream.Stream;

public enum ImageKindFolder
{
    USERS_PATH(ImageKind.USER, "users"),
    VEHICLES_PATH(ImageKind.VEHICLE, "vehicles");

    private final ImageKind imageKind;
    private final String folderName;

    ImageKindFolder(ImageKind imageKind, String folderName)
    {
        this.imageKind = imageKind;
        this.folderName = folderName;
    }

    public ImageKind getImageKind()
    {
        return imageKind;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public static ImageKindFolder getByImageKind(ImageKind imageKind)
    {
        return Stream.of(values())
                .filter(imageKindFolder -> imageKindFolder.getImageKind().equals(imageKind))
                .findFirst()
                .orElseThrow();
    }
}
