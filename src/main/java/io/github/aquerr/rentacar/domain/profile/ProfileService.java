package io.github.aquerr.rentacar.domain.profile;

import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.security.exception.AccessDeniedException;
import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.model.ImageKind;
import io.github.aquerr.rentacar.domain.profile.converter.ProfileConverter;
import io.github.aquerr.rentacar.domain.profile.dto.CreateProfileParameters;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.domain.profile.exception.ProfileNotFoundException;
import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;
    private final AuthenticationFacade authenticationFacade;
    private final ImageService imageService;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserProfile> getProfiles() {
        return profileRepository.findAll().stream()
                .map(profileConverter::toProfileDto)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserProfile getProfileById(Long id) {
        return profileRepository.findById(id)
                .map(profileConverter::toProfileDto)
                .orElseThrow(ProfileNotFoundException::new);
    }

    @Transactional
    public void createNewProfile(CreateProfileParameters createProfileParameters)
    {
        this.profileRepository.save(UserProfileEntity.builder()
                        .credentialsId(createProfileParameters.getCredentialsId())
                        .email(createProfileParameters.getEmail())
                .build());
    }

    @Transactional
    public void saveProfile(UserProfile userProfileDto)
    {
        if (!canCurrentUserEditUserProfile(userProfileDto))
        {
            throw new AccessDeniedException();
        }

        UserProfileEntity userProfile = this.profileConverter.toProfile(userProfileDto);
        userProfile.setCredentialsId(this.authenticationFacade.getCurrentUser().getId());
        this.profileRepository.save(userProfile);
    }

    private boolean canCurrentUserEditUserProfile(UserProfile userProfile)
    {
        return this.authenticationFacade.getCurrentUser().getProfileId().equals(userProfile.getId());
    }

    @Transactional
    public void saveProfileWithImage(UserProfile userProfile, MultipartFile image)
    {
        String oldImageName = imageService.retrieveImageName(userProfile.getIconUrl());
        userProfile.setIconUrl(imageService.saveImage(image, ImageKind.USER).getUri().toString());
        saveProfile(userProfile);
        imageService.deleteImage(ImageKind.USER, oldImageName);
    }
}
