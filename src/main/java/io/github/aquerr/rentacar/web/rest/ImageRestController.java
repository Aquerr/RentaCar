package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.image.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@AllArgsConstructor
public class ImageRestController
{

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> saveImage(@RequestPart("image") MultipartFile file, @RequestParam String path)
    {
        imageService.saveImage(file, path);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteImage(@RequestParam String path)
    {
        imageService.deleteImage(path);
        return ResponseEntity.ok().build();
    }
}
