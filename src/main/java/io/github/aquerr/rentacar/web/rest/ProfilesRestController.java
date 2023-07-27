package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfileDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api/v1/profiles")
@RestController
public class ProfilesRestController
{
    private final ProfileService profileService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> saveProfile(@PathVariable("id") long profileId, @RequestBody UserProfileDto userProfileDto)
    {
        userProfileDto.setId(profileId);
        profileService.saveProfile(userProfileDto);
        return ResponseEntity.noContent().build();
    }
}
