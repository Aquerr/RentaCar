package io.github.aquerr.rentacar.domain.profile.converter;

import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter {
    public UserProfile toProfileDto(UserProfileEntity profile) {
        if (profile == null) {
            return null;
        }

        return UserProfile.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .birthDate(profile.getBirthDate())
                .city(profile.getCity())
                .zipCode(profile.getZipCode())
                .street(profile.getStreet())
                .phoneNumber(profile.getPhoneNumber())
                .iconUrl(profile.getIconUrl())
                .build();
    }

    public UserProfileEntity toProfile(UserProfile profileDto) {
        if (profileDto == null) {
            return null;
        }

        return UserProfileEntity.builder()
                .id(profileDto.getId())
                .firstName(profileDto.getFirstName())
                .lastName(profileDto.getLastName())
                .email(profileDto.getEmail())
                .birthDate(profileDto.getBirthDate())
                .city(profileDto.getCity())
                .zipCode(profileDto.getZipCode())
                .street(profileDto.getStreet())
                .phoneNumber(profileDto.getPhoneNumber())
                .iconUrl(profileDto.getIconUrl())
                .build();
    }
}
