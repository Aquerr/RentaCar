package io.github.aquerr.rentacar.domain.profile;

import io.github.aquerr.rentacar.domain.profile.converter.ProfileConverter;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfileDto;
import io.github.aquerr.rentacar.domain.profile.exception.ProfileNotFoundException;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDto> getProfiles() {
        return profileRepository.findAll().stream()
                .map(profileConverter::toProfileDto)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserProfileDto getProfileById(Long id) {
        return profileRepository.findById(id)
                .map(profileConverter::toProfileDto)
                .orElseThrow(ProfileNotFoundException::new);
    }
}
