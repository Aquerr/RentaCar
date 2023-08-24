package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.security.AuthenticationFacade;
import io.github.aquerr.rentacar.application.security.RentaCarAuthenticationManager;
import io.github.aquerr.rentacar.application.security.exception.AccessDeniedException;
import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.web.rest.request.MfaActivationRequest;
import io.github.aquerr.rentacar.web.rest.response.MfaActivationResponse;
import io.github.aquerr.rentacar.web.rest.response.MfaTotpQrDataUriResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> saveProfile(@PathVariable("id") long profileId,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                         @RequestPart(value = "profile") UserProfile userProfile)
    {
        validateUserAccess(profileId);

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

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable("id") long profileId)
    {
        return ResponseEntity.ok(profileService.getProfileById(profileId));
    }

    @GetMapping("/{id}/settings/mfa/activation")
    public ResponseEntity<MfaTotpQrDataUriResponse> generateQrCode(@PathVariable("id") long profileId,
                                                                   @RequestParam("type") MfaType mfaType)
    {
        //TODO: Handle mfaType...

        validateUserAccess(profileId);
        return ResponseEntity.ok(MfaTotpQrDataUriResponse.of(authenticationManager.generateMfaQrData(authenticationFacade.getCurrentUser())));
    }

    @DeleteMapping("/{id}/settings/mfa")
    public ResponseEntity<?> deleteMfa(@PathVariable("id") long profileId)
    {
        validateUserAccess(profileId);
        authenticationManager.deleteMfa(authenticationFacade.getCurrentUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/settings/mfa/activation")
    public ResponseEntity<MfaActivationResponse> activateMfa(@PathVariable("id") long profileId,
                                                             @RequestBody MfaActivationRequest mfaActivationRequest)
    {
        validateUserAccess(profileId);
        return ResponseEntity.ok(MfaActivationResponse.of(
                authenticationManager.activateMfa(authenticationFacade.getCurrentUser(), mfaActivationRequest.getCode()))
        );
    }

    private void validateUserAccess(long profileId)
    {
        if (profileId == authenticationFacade.getCurrentUser().getProfileId())
        {
            throw new AccessDeniedException();
        }
    }
}
