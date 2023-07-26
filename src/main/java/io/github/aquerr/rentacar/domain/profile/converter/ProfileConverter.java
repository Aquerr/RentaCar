package io.github.aquerr.rentacar.domain.profile.converter;

import io.github.aquerr.rentacar.domain.profile.dto.UserProfileDto;
import io.github.aquerr.rentacar.domain.profile.model.RentaCarUserProfile;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter {
    public UserProfileDto toProfileDto(RentaCarUserProfile profile) {
        if (profile == null) {
            return null;
        }

        return UserProfileDto.builder()
                .id(profile.getId())
                .birthDate(profile.getBirthDate())
                .city(profile.getCity())
                .build();
    }

    public RentaCarUserProfile toProfile(UserProfileDto profileDto) {
        if (profileDto == null) {
            return null;
        }

        return RentaCarUserProfile.builder()
                .id(profileDto.getId())
                .birthDate(profileDto.getBirthDate())
                .city(profileDto.getCity())
                .build();
    }
}
