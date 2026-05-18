package io.github.aquerr.rentacar.domain.vehicle;

import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.dto.ImageUri;
import io.github.aquerr.rentacar.domain.image.model.ImageKind;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleBasicData;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleFullData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
@AllArgsConstructor
public class VehicleImagePopulator
{
    private final ImageService imageService;

    public void populate(VehicleBasicData.VehicleBasicDataBuilder vehicleBasicDataBuilder, List<String> photoNames)
    {
        if (!photoNames.isEmpty())
        {
            vehicleBasicDataBuilder.photoUrl(imageService.getImageUri(ImageKind.VEHICLE, photoNames.get(0)).getUri().toString());
        }
    }

    public void populate(VehicleEntity.VehicleEntityBuilder vehicleEntityBuilder, List<String> photoUrls)
    {
        //TODO: Properly handle multiple photos...
        vehicleEntityBuilder.photoNames(photoUrls.stream()
                .map(imageService::retrieveImageName)
                .toList());
    }

    public void populate(VehicleFullData.VehicleFullDataBuilder vehicleFullDataBuilder, List<String> photoNames)
    {
        vehicleFullDataBuilder.photosUrls(photoNames.stream()
                .map(photoName -> imageService.getImageUri(ImageKind.VEHICLE, photoName))
                .map(ImageUri::getUri)
                .map(URI::toString)
                .toList());
    }
}
