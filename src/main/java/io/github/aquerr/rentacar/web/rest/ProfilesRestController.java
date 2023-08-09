package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.model.ImageKind;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RequestMapping("/api/v1/profiles")
@RestController
public class ProfilesRestController
{
    private final ProfileService profileService;
    private final ImageService imageService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> saveProfile(@PathVariable("id") long profileId,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                         @RequestParam(value = "imageKind", required = false) ImageKind imageKind,
                                         @RequestParam(value = "profile") UserProfile userProfile)
    {
        userProfile.setId(profileId);

        if (image != null && imageKind != null)
        {
            userProfile.setIconUrl(imageService.saveImage(image, imageKind).getUri().toString());
        }

        profileService.saveProfile(userProfile);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable("id") long profileId)
    {
        return ResponseEntity.ok(profileService.getProfileById(profileId));
    }
}
