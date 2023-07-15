package io.github.aquerr.rentacar.domain.profile.converter;

import io.github.aquerr.rentacar.domain.profile.dto.ProfileDto;
import io.github.aquerr.rentacar.domain.profile.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter {
    public ProfileDto toProfileDto(UserProfile profile) {
        if (profile == null) {
            return null;
        }

        return ProfileDto.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .birthDate(profile.getBirthDate())
                .city(profile.getCity())
                .build();
    }

    public UserProfile toProfile(ProfileDto profileDto) {
        if (profileDto == null) {
            return null;
        }

        return UserProfile.builder()
                .id(profileDto.getId())
                .userId(profileDto.getUserId())
                .birthDate(profileDto.getBirthDate())
                .city(profileDto.getCity())
                .build();
    }
}
