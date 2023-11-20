package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.security.RentaCarAuthenticationManager;
import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import io.github.aquerr.rentacar.application.security.mfa.dto.UserMfaSettings;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.web.rest.request.MfaActivationRequest;
import io.github.aquerr.rentacar.web.rest.response.MfaActivationResponse;
import io.github.aquerr.rentacar.web.rest.response.MfaSettingsResponse;
import io.github.aquerr.rentacar.web.rest.response.MfaTotpQrDataUriResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RequestMapping("/api/v1/profiles")
@RestController
public class ProfilesRestController
{
    private final ProfileService profileService;
    private final AuthenticationFacade authenticationFacade;
    private final RentaCarAuthenticationManager authenticationManager;

    @PatchMapping(value = "/{profileId}")
    @PreAuthorize("@securityManager.canEditProfile(authentication, #profileId)")
    public ResponseEntity<?> saveProfile(@PathVariable("profileId") long profileId,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                         @RequestPart(value = "profile") UserProfile userProfile)
    {
        userProfile.setId(profileId);

        if (image != null)
        {
            profileService.saveProfileWithImage(userProfile, image);
        }
        else
        {
            profileService.saveProfile(userProfile);
        }

        return getProfile(profileId);
    }

    @GetMapping("/{profileId}")
    @PreAuthorize("@securityManager.canEditProfile(authentication, #profileId)")
    public ResponseEntity<UserProfile> getProfile(@PathVariable("profileId") long profileId)
    {
        return ResponseEntity.ok(profileService.getProfileById(profileId));
    }

    @GetMapping("/{profileId}/settings/mfa/activation")
    @PreAuthorize("@securityManager.canEditProfile(authentication, #profileId)")
    public ResponseEntity<MfaTotpQrDataUriResponse> generateQrCode(@PathVariable("profileId") long profileId,
                                                                   @RequestParam(value = "type") MfaType mfaType)
    {
        //TODO: Handle mfaType...
        return ResponseEntity.ok(MfaTotpQrDataUriResponse.of(authenticationManager.generateMfaQrData(authenticationFacade.getCurrentUser())));
    }

    @DeleteMapping("/{profileId}/settings/mfa")
    @PreAuthorize("@securityManager.canEditProfile(authentication, #profileId)")
    public ResponseEntity<?> deleteMfa(@PathVariable("profileId") long profileId)
    {
        authenticationManager.deleteMfa(authenticationFacade.getCurrentUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{profileId}/settings/mfa/activation")
    @PreAuthorize("@securityManager.canEditProfile(authentication, #profileId)")
    public ResponseEntity<MfaActivationResponse> activateMfa(@PathVariable("profileId") long profileId,
                                                             @RequestBody MfaActivationRequest mfaActivationRequest)
    {
        return ResponseEntity.ok(MfaActivationResponse.of(
                authenticationManager.activateMfa(authenticationFacade.getCurrentUser(), mfaActivationRequest.getCode()))
        );
    }

    @GetMapping("/{profileId}/mfa-settings")
    @PreAuthorize("@securityManager.canEditProfile(authentication, #profileId)")
    public ResponseEntity<MfaSettingsResponse> getUserMfaSettings(@PathVariable("profileId") long profileId)
    {
        UserMfaSettings userMfaSettings = authenticationManager.getUserMfaSettings(authenticationFacade.getCurrentUser());
        if (userMfaSettings != null) {
            return ResponseEntity.ok(MfaSettingsResponse.of(userMfaSettings.getMfaType(), userMfaSettings.isVerified()));
        }
        return ResponseEntity.ok(null);
    }
}
