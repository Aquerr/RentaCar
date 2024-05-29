package io.github.aquerr.rentacar.domain.profile;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.domain.image.ImageService;
import io.github.aquerr.rentacar.domain.image.dto.ImageUri;
import io.github.aquerr.rentacar.domain.image.model.ImageKind;
import io.github.aquerr.rentacar.domain.profile.converter.ProfileConverter;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest
{
    private static final long AUTH_USER_PROFILE_ID = 2;

    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private ImageService imageService;
    @Mock
    private ProfileConverter profileConverter;
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void shouldSaveProfile()
    {
        // given
        UserProfile userProfile = UserProfile.builder()
                .id(AUTH_USER_PROFILE_ID)
                .build();

        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .userId(AUTH_USER_PROFILE_ID)
                .build();
        given(authenticationFacade.getCurrentUser()).willReturn(prepareAuthenticatedUser());
        given(this.profileConverter.toProfile(UserProfile.builder()
                .id(AUTH_USER_PROFILE_ID)
                .build())
        ).willReturn(userProfileEntity);

        // when
        profileService.saveProfile(userProfile);

        // then
        verify(profileRepository).save(userProfileEntity);
    }

    @Test
    void shouldSaveProfileWithImageSaveNewImageAndProfileAndDeleteOldImage()
    {
        // given
        UserProfile userProfile = UserProfile.builder()
                .id(AUTH_USER_PROFILE_ID)
                .iconUrl("http://test.com/myiconurl.png")
                .build();
        MultipartFile image = mock(MultipartFile.class);
        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .userId(AUTH_USER_PROFILE_ID)
                .iconName("newImage")
                .build();

        given(imageService.saveImage(image, ImageKind.USER)).willReturn(ImageUri.of(URI.create("http://test.com/USER/newicon.png")));
        given(imageService.retrieveImageName("http://test.com/myiconurl.png")).willReturn("myImage");
        given(authenticationFacade.getCurrentUser()).willReturn(prepareAuthenticatedUser());
        given(this.profileConverter.toProfile(UserProfile.builder()
                .id(AUTH_USER_PROFILE_ID)
                .iconUrl("http://test.com/USER/newicon.png")
                .build())
        ).willReturn(userProfileEntity);

        // when
        profileService.saveProfileWithImage(userProfile, image);

        // then
        verify(imageService).deleteImage(ImageKind.USER, "myImage");
        verify(profileRepository).save(userProfileEntity);
    }

    private AuthenticatedUser prepareAuthenticatedUser()
    {
        return new AuthenticatedUser(
                1L,
                "test_user",
                "test_pass",
                "test_ip",
                Collections.emptySet()
        );
    }
}