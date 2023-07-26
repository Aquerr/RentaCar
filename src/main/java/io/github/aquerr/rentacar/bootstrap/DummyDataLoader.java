package io.github.aquerr.rentacar.bootstrap;

import io.github.aquerr.rentacar.domain.profile.model.RentaCarUserProfile;
import io.github.aquerr.rentacar.domain.user.model.RentaCarUserCredentials;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

/**
 * Populates database with some dummy data.
 */
@Component
@Profile({"dummy-data", "!prod"})
@AllArgsConstructor
public class DummyDataLoader implements CommandLineRunner
{
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception
    {
        profileRepository.deleteAll();
        userRepository.deleteAll();

        RentaCarUserCredentials rentaCarUserCredentials1 = new RentaCarUserCredentials(1L,
                "test_user",
                "test_email@test.com",
                passwordEncoder.encode("test_pass"),
                Set.of(),
                true,
                false);
        userRepository.save(rentaCarUserCredentials1);
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

        RentaCarUserCredentials rentaCarUserCredentials2 = new RentaCarUserCredentials(2L,
                "tester2",
                "testujemy@omg.com",
                passwordEncoder.encode("testujemy"),
                Set.of(),
                false,
                false);
        userRepository.save(rentaCarUserCredentials2);
        RentaCarUserProfile rentaCarUserProfile2 = new RentaCarUserProfile(2L,
                rentaCarUserCredentials2.getId(),
                "Tester",
                "Niezweryfikowany",
                "111111111",
                rentaCarUserCredentials1.getEmail(),
                LocalDate.of(1969, 6, 9),
                "Testów",
                "13255",
                "Fantazyjna 69");
        profileRepository.save(rentaCarUserProfile2);
    }
}
