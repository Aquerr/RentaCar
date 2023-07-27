package io.github.aquerr.rentacar.bootstrap;

import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenDto;
import io.github.aquerr.rentacar.domain.profile.model.RentaCarUserProfile;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.domain.user.dto.UserDto;
import io.github.aquerr.rentacar.domain.user.model.RentaCarUserCredentials;
import io.github.aquerr.rentacar.domain.user.model.RentacarAuthority;
import io.github.aquerr.rentacar.domain.user.model.UserRegistration;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

/**
 * Populates database with some dummy data.
 */
@Component
@Profile({"dummy-data", "!prod"})
@AllArgsConstructor
@Slf4j
public class DummyDataLoader implements CommandLineRunner
{
    private final UserService userService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception
    {
        profileRepository.deleteAll();
        userCredentialsRepository.deleteAll();

        RentaCarUserCredentials rentaCarUserCredentials1 = new RentaCarUserCredentials(1L,
                "test_user",
                "test_email@test.com",
                passwordEncoder.encode("test_pass"),
                Set.of(RentacarAuthority.EDIT_CARS.getAuthority(), RentacarAuthority.VIEW_CAR_LOCATION.getAuthority()),
                true,
                false);
        userCredentialsRepository.save(rentaCarUserCredentials1);
        RentaCarUserProfile rentaCarUserProfile1 = new RentaCarUserProfile(1L,
                rentaCarUserCredentials1.getId(),
                "Tester",
                "Testowski",
                "999999999",
                rentaCarUserCredentials1.getEmail(),
                LocalDate.of(1999, 6, 15),
                "Testów",
                "15551",
                "Wymyślna 42");
        profileRepository.save(rentaCarUserProfile1);
        log.info("Created dummy verified profile: {}", rentaCarUserCredentials1);

        UserRegistration userRegistration = new UserRegistration("tester2", "testujemy@omg.com", "testujemy");
        ActivationTokenDto activationTokenDto = userService.register(userRegistration);
        log.info("Created dummy not-verified profile: {}, activation-token: {}", userRegistration, activationTokenDto);
    }
}
