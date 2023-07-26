package io.github.aquerr.rentacar.domain.user.converter;

import io.github.aquerr.rentacar.domain.user.dto.UserDto;
import io.github.aquerr.rentacar.domain.user.model.RentaCarUserCredentials;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convertToDto(RentaCarUserCredentials user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public RentaCarUserCredentials convertToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return RentaCarUserCredentials.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }
}
