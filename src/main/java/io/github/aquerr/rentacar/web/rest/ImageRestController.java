package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.model.ImageKind;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.aquerr.rentacar.domain.image.ImageService.API_IMAGE_URL;

@RestController
@RequestMapping(API_IMAGE_URL)
@AllArgsConstructor
public class ImageRestController
{

    private final ImageService imageService;

    @GetMapping(value = "/{kind}/{name}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("kind") ImageKind imageKind, @PathVariable("name") String name)
    {
        return ResponseEntity.ok(imageService.getImageBytes(imageKind, name));
    }
}
