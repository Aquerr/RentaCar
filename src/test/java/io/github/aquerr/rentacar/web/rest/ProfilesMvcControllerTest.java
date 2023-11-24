package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.application.security.AuthenticatedUser;
import io.github.aquerr.rentacar.application.security.dto.MfaActivationResult;
import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import io.github.aquerr.rentacar.domain.profile.dto.UserProfile;
import io.github.aquerr.rentacar.util.TestResourceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ProfilesRestController.class})
class ProfilesMvcControllerTest extends BaseMvcIntegrationTest
{
    private static final long USER_ID_1 = 1;
    private static final String USERNAME = "test_user";
    private static final long PROFILE_ID_1 = 1;

    @Test
    void shouldGetUserProfile() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(profileService.getProfileById(PROFILE_ID_1)).willReturn(prepareUserProfile(PROFILE_ID_1));

        // when
        // then
        mockMvc.perform(get("/api/v1/profiles/" + PROFILE_ID_1)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/get_profile.json"), true));
    }

    @Test
    void shouldReturnErrorWhenGettingProfileForDifferentUser() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(profileService.getProfileById(PROFILE_ID_1)).willReturn(prepareUserProfile(PROFILE_ID_1));

        // when
        // then
        mockMvc.perform(get("/api/v1/profiles/" + 2)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldSaveUserProfile() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        UserProfile updatedProfile = objectMapper.readValue(TestResourceUtils.loadMockJson("mock-json/save_profile_response.json"), UserProfile.class);

        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(profileService.getProfileById(PROFILE_ID_1)).willReturn(updatedProfile);

        // when
        // then
        mockMvc.perform(multipart(HttpMethod.PATCH, "/api/v1/profiles/" + PROFILE_ID_1)
                        .file(new MockMultipartFile("image", TestResourceUtils.loadImage("mock-image/photo.jpg")))
                        .file(new MockMultipartFile("profile", "profile.json", "application/json", TestResourceUtils.loadMockJson("mock-json/save_profile_request.json").getBytes()))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/save_profile_response.json"), true));
        verify(profileService).saveProfileWithImage(any(UserProfile.class), any(MultipartFile.class));
    }

    @Test
    void shouldSaveUserProfileWithoutImage() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        UserProfile updatedProfile = objectMapper.readValue(TestResourceUtils.loadMockJson("mock-json/save_profile_response.json"), UserProfile.class);

        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(profileService.getProfileById(PROFILE_ID_1)).willReturn(updatedProfile);

        // when
        // then
        mockMvc.perform(multipart(HttpMethod.PATCH, "/api/v1/profiles/" + PROFILE_ID_1)
                        .file(new MockMultipartFile("profile", "profile.json", "application/json", TestResourceUtils.loadMockJson("mock-json/save_profile_request.json").getBytes()))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/save_profile_response.json"), true));
        verify(profileService).saveProfile(any(UserProfile.class));
    }

    @Test
    void shouldReturnErrorWhenSavingProfileForDifferentUser() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        UserProfile updatedProfile = objectMapper.readValue(TestResourceUtils.loadMockJson("mock-json/save_profile_response.json"), UserProfile.class);

        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(profileService.getProfileById(PROFILE_ID_1)).willReturn(updatedProfile);

        // when
        // then
        mockMvc.perform(multipart(HttpMethod.PATCH, "/api/v1/profiles/" + 2)
                        .file(new MockMultipartFile("image", TestResourceUtils.loadImage("mock-image/photo.jpg")))
                        .file(new MockMultipartFile("profile", "profile.json", "application/json", TestResourceUtils.loadMockJson("mock-json/save_profile_request.json").getBytes()))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden());
        verifyNoInteractions(profileService);
    }

    @Test
    void shouldReturnMfaQrUri() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(authenticationManager.prepareMfaActivation(any(), any(MfaType.class))).willReturn("data:image/png;base64,SGVsbG8sIFdvcmxkIQ==");

        // when
        // then
        mockMvc.perform(get("/api/v1/profiles/" + PROFILE_ID_1 + "/settings/mfa/activation")
                        .param("type", "TOTP")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/get_qr_data_uri.json")));
    }

    @Test
    void shouldReturnErrorWhenGettingMfaQrUriForDifferentUser() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(authenticationManager.prepareMfaActivation(any(), any(MfaType.class))).willReturn("data:image/png;base64,SGVsbG8sIFdvcmxkIQ==");

        // when
        // then
        mockMvc.perform(get("/api/v1/profiles/2/settings/mfa/activation")
                        .param("type", "TOTP")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden());
        verify(authenticationManager, never()).prepareMfaActivation(any(AuthenticatedUser.class), any(MfaType.class));
    }

    @Test
    void shouldDeleteMfa() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));

        // when
        // then
        mockMvc.perform(delete("/api/v1/profiles/" + PROFILE_ID_1 + "/settings/mfa")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
        verify(authenticationManager).deleteMfa(any());
    }

    @Test
    void shouldReturnErrorWhenDeletingMfaForDifferentUser() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));

        // when
        // then
        mockMvc.perform(delete("/api/v1/profiles/2/settings/mfa")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden());
        verify(authenticationManager, never()).deleteMfa(any());
    }

    @Test
    void shouldActivateMfaAndReturnResponse() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(authenticationManager.activateMfa(any(), any())).willReturn(MfaActivationResult.of(Set.of("123456", "323232", "546456", "111111", "999999", "000000")));

        // when
        // then
        mockMvc.perform(post("/api/v1/profiles/" + PROFILE_ID_1 + "/settings/mfa/activation")
                        .contentType("application/json")
                        .content(TestResourceUtils.loadMockJson("mock-json/activate_mfa_request.json"))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/activate_mfa_response.json")));
    }

    @Test
    void shouldReturnErrorWhenActivatingMfaForDifferentUser() throws Exception
    {
        // given
        String jwt = jwtService.createJwt(USERNAME, false, Set.of());
        given(rentaCarUserDetailsService.loadUserByUsername(USERNAME)).willReturn(prepareAuthenticatedUser(USER_ID_1, USERNAME, PROFILE_ID_1, Set.of()));
        given(authenticationManager.activateMfa(any(), any())).willReturn(MfaActivationResult.of(Set.of("123456", "323232", "546456", "111111", "999999", "000000")));

        // when
        // then
        mockMvc.perform(post("/api/v1/profiles/2/settings/mfa/activation")
                        .contentType("application/json")
                        .content(TestResourceUtils.loadMockJson("mock-json/activate_mfa_request.json"))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden());
    }

    private UserProfile prepareUserProfile(long profileId)
    {
        return UserProfile.builder()
                .id(profileId)
                .firstName("test_name")
                .lastName("test_last_name")
                .iconUrl("http://iconurl.png")
                .city("test_city")
                .birthDate(LocalDate.of(1999, 4, 12))
                .street("test_street")
                .email("test_email")
                .phoneNumber("123456789")
                .zipCode("12345")
                .build();
    }
}