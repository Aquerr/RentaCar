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
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .birthDate(profile.getBirthDate())
                .city(profile.getCity())
                .zipCode(profile.getZipCode())
                .street(profile.getStreet())
                .phoneNumber(profile.getPhoneNumber())
                .build();
    }

    public RentaCarUserProfile toProfile(UserProfileDto profileDto) {
        if (profileDto == null) {
            return null;
        }

        return RentaCarUserProfile.builder()
                .id(profileDto.getId())
                .firstName(profileDto.getFirstName())
                .lastName(profileDto.getLastName())
                .email(profileDto.getEmail())
                .birthDate(profileDto.getBirthDate())
                .city(profileDto.getCity())
                .zipCode(profileDto.getZipCode())
                .street(profileDto.getStreet())
                .phoneNumber(profileDto.getPhoneNumber())
                .build();
    }
}
