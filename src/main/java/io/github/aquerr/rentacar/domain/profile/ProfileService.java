package io.github.aquerr.rentacar.domain.profile;

import io.github.aquerr.rentacar.domain.profile.converter.ProfileConverter;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfileDto;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;
    private final UserRepository userRepository;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserProfileDto getProfileByUsername(String username)
    {
        long credentialsId = userRepository.findByUsername(username).getId();
        return profileConverter.toProfileDto(profileRepository.findByCredentialsId(credentialsId));
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDto> getProfiles() {
        return profileRepository.findAll().stream()
                .map(profileConverter::toProfileDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserProfileDto getProfileById(Long id) {
        return profileRepository.findById(id)
                .map(profileConverter::toProfileDto)
                .orElse(null);
    }
}
