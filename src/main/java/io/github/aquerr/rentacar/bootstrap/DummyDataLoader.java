package io.github.aquerr.rentacar.bootstrap;

import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.domain.user.model.Authority;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.model.UserRegistration;
import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import io.github.aquerr.rentacar.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Populates database with some dummy data.
 */
@Component
@Profile({"dummy-data"})
@AllArgsConstructor
@Slf4j
public class DummyDataLoader implements CommandLineRunner {
    private final UserService userService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {
        profileRepository.deleteAll();
        userCredentialsRepository.deleteAll();
        vehicleRepository.deleteAll();

        UserCredentialsEntity userCredentialsEntity1 = new UserCredentialsEntity(1L,
                "test_user",
                "test_email@test.com",
                passwordEncoder.encode("test_pass"),
                Set.of(Authority.EDIT_CARS.getAuthority(), Authority.VIEW_CAR_LOCATION.getAuthority()),
                true,
                false);
        userCredentialsRepository.save(userCredentialsEntity1);
        UserProfileEntity userProfileEntity1 = new UserProfileEntity(1L,
                userCredentialsEntity1.getId(),
                "Tester",
                "Testowski",
                "999999999",
                userCredentialsEntity1.getEmail(),
                LocalDate.of(1999, 6, 15),
                "Testów",
                "15551",
                "Wymyślna 42",
                "1/photo.jpg");
        profileRepository.save(userProfileEntity1);
        log.info("Created dummy verified profile: {}", userCredentialsEntity1);

        UserRegistration userRegistration = new UserRegistration("tester2", "testing@test.com", "testujemy");
        userService.register(userRegistration);

        VehicleEntity vehicle = VehicleEntity.builder()
                .id(1)
                .brand("BRAND")
                .model("MODEL")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("red")
                .rimsInch(21)
                .capacity(1988)
                .engineType("GAS")
                .power(211)
                .torque(422)
                .avgFuelConsumption(6.5F)
                .transmission("MANUAL")
                .ac(true)
                .frontPDC(true)
                .rearPDC(true)
                .bluetooth(true)
                .ledFrontLights(true)
                .ledRearLights(false)
                .xenonFrontLights(true)
                .leatherSeats(true)
                .multifunctionalSteeringWheel(true)
                .category("A")
                .basicPrice(new BigDecimal(245))
                .photoNames("1/photo.jpg")
                .build();
        vehicleRepository.save(vehicle);
    }
}
