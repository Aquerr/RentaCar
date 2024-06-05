package io.github.aquerr.rentacar;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
@ActiveProfiles({"test"})
public abstract class BaseIntegrationTest
{
    public static final String USERNAME = "username";
    public static final String EMAIL = "Email";

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .findAndRegisterModules();

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @LocalServerPort
    protected int serverPort;

    protected UserEntity givenActivatedUser(UserProfileEntity userProfileEntity)
    {
        return givenActivatedUser(prepareActivatedUserCredentials(), userProfileEntity);
    }

    protected UserEntity givenActivatedUser(UserCredentialsEntity userCredentialsEntity)
    {
        return givenActivatedUser(userCredentialsEntity, prepareUserProfile());
    }

    protected UserEntity givenActivatedUser()
    {
        return givenActivatedUser(prepareActivatedUserCredentials(), prepareUserProfile());
    }

    protected UserEntity givenActivatedUser(UserCredentialsEntity userCredentialsEntity,
                                            UserProfileEntity userProfileEntity)
    {
        UserEntity userEntity = UserEntity.builder()
                .credentials(userCredentialsEntity)
                .profile(userProfileEntity)
                .build();

        userCredentialsEntity.setUser(userEntity);
        userProfileEntity.setUser(userEntity);

        return userRepository.saveAndFlush(userEntity);
    }

    protected UserProfileEntity prepareUserProfile()
    {
        return UserProfileEntity.builder()
                .build();
    }

    protected UserCredentialsEntity prepareUserCredentials(String username, String email, boolean activated, ZonedDateTime activatedDateTime)
    {
        return UserCredentialsEntity.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode("test"))
                .activated(activated)
                .activatedDateTime(activatedDateTime)
                .build();
    }

    protected UserCredentialsEntity prepareActivatedUserCredentials()
    {
        return prepareUserCredentials(
                USERNAME,
                EMAIL,
                true,
                ZonedDateTime.of(LocalDateTime.of(2024, 4, 20, 10, 15), ZoneId.of("Europe/Warsaw"))
        );
    }
}
