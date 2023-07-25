package io.github.aquerr.rentacar.domain.user;

import io.github.aquerr.rentacar.domain.user.converter.UserConverter;
import io.github.aquerr.rentacar.domain.user.dto.UserDto;
import io.github.aquerr.rentacar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDto findByUsername(String username)
    {
        return userConverter.convertToDto(userRepository.findByUsername(username));
    }
}
