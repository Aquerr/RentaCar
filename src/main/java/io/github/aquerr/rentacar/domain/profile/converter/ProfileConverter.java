package io.github.aquerr.rentacar.domain.profile.converter;

import io.github.aquerr.rentacar.domain.profile.ProfileImagePopulator;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfileConverter {

    private final ProfileImagePopulator profileImagePopulator;

    public UserProfile toProfileDto(UserProfileEntity profile) {
        if (profile == null) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfileBuilder = UserProfile.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .birthDate(profile.getBirthDate())
                .city(profile.getCity())
                .zipCode(profile.getZipCode())
                .street(profile.getStreet())
                .phoneNumber(profile.getPhoneNumber());

        profileImagePopulator.populate(userProfileBuilder, profile.getIconName());
        return userProfileBuilder.build();
    }

    public UserProfileEntity toProfile(UserProfile profileDto) {
        if (profileDto == null) {
            return null;
        }

        UserProfileEntity.UserProfileEntityBuilder userProfileEntityBuilder = UserProfileEntity.builder()
                .id(profileDto.getId())
                .firstName(profileDto.getFirstName())
                .lastName(profileDto.getLastName())
                .email(profileDto.getEmail())
                .birthDate(profileDto.getBirthDate())
                .city(profileDto.getCity())
                .zipCode(profileDto.getZipCode())
                .street(profileDto.getStreet())
                .phoneNumber(profileDto.getPhoneNumber());

        profileImagePopulator.populate(userProfileEntityBuilder, profileDto.getIconUrl());

        return userProfileEntityBuilder.build();
    }
}
