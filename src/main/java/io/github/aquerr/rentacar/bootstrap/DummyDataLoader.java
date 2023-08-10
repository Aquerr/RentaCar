package io.github.aquerr.rentacar.bootstrap;

import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.domain.user.dto.UserRegistration;
import io.github.aquerr.rentacar.domain.user.model.Authority;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
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
import java.util.Collections;
import java.util.List;
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

        createDummyUsers();
        createDummyVehicles();
    }

    private void createDummyUsers()
    {
        UserCredentialsEntity userCredentialsEntity1 = UserCredentialsEntity.builder()
                .username("test_user")
                .email("test_email@test.com")
                .password(passwordEncoder.encode("test_pass"))
                .authorities(Collections.emptySet())
                .activated(true)
                .locked(false)
                .build();
        userCredentialsEntity1 = userCredentialsRepository.save(userCredentialsEntity1);

        UserCredentialsEntity adminCredentials = UserCredentialsEntity.builder()
                .username("test_admin")
                .email("admin_email@test.com")
                .password(passwordEncoder.encode("test_admin"))
                .authorities(Set.of(Authority.EDIT_CARS.getAuthority(), Authority.VIEW_CAR_LOCATION.getAuthority(), Authority.VIEW_ADMIN_PANEL.getAuthority()))
                .activated(true)
                .locked(false)
                .build();
        adminCredentials = userCredentialsRepository.save(adminCredentials);
        UserProfileEntity adminUserProfile = UserProfileEntity.builder()
                .credentialsId(adminCredentials.getId())
                .firstName("Admin")
                .lastName("Testowski")
                .phoneNumber("111222333")
                .email(adminCredentials.getEmail())
                .birthDate(LocalDate.of(1986, 2, 5))
                .city("Testów")
                .zipCode("14239")
                .street("Wymyślna 92")
                .iconName("photo.jpg")
                .build();
        profileRepository.save(adminUserProfile);
        log.info("Created dummy verified admin profile: {}", adminUserProfile);

        UserProfileEntity userProfileEntity1 = UserProfileEntity.builder()
                .credentialsId(userCredentialsEntity1.getId())
                .firstName("Tester")
                .lastName("Testowski")
                .phoneNumber("999999999")
                .email(userCredentialsEntity1.getEmail())
                .birthDate(LocalDate.of(1999, 6, 15))
                .city("Testów")
                .zipCode("15551")
                .street("Wymyślna 42")
                .iconName("photo.jpg")
                .build();
        profileRepository.save(userProfileEntity1);
        log.info("Created dummy verified profile: {}", userCredentialsEntity1);

        UserRegistration userRegistration = new UserRegistration("tester2", "testing@test.com", "testujemy");
        userService.register(userRegistration);
    }

    private void createDummyVehicles()
    {
        VehicleEntity vehicle = VehicleEntity.builder()
                .id(1)
                .brand("TOYOTA")
                .model("YARIS")
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
                .category("C")
                .basicPrice(new BigDecimal(245))
                .photoNames(List.of("car1.webp"))
                .build();
        VehicleEntity vehicle2 = VehicleEntity.builder()
                .id(2)
                .brand("TOYOTA")
                .model("AVENSIS")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("red")
                .rimsInch(21)
                .capacity(1988)
                .engineType("GAS")
                .power(211)
                .torque(422)
                .avgFuelConsumption(4.5F)
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
                .category("B")
                .basicPrice(new BigDecimal(145))
                .photoNames(List.of("car2.webp"))
                .build();
        VehicleEntity vehicle3 = VehicleEntity.builder()
                .id(3)
                .brand("LEXUS")
                .model("MODEL")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("red")
                .rimsInch(21)
                .capacity(1988)
                .engineType("DIESEL")
                .power(211)
                .torque(422)
                .avgFuelConsumption(6.8F)
                .transmission("AUTOMATIC")
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
                .basicPrice(new BigDecimal(545))
                .photoNames(List.of("car3.webp"))
                .build();
        VehicleEntity vehicle4 = VehicleEntity.builder()
                .id(4)
                .brand("AUDI")
                .model("A6")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("white")
                .rimsInch(21)
                .capacity(1988)
                .engineType("GAS")
                .power(211)
                .torque(422)
                .avgFuelConsumption(8.5F)
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
                .photoNames(List.of("car4.webp"))
                .build();
        VehicleEntity vehicle5 = VehicleEntity.builder()
                .id(5)
                .brand("AUDI")
                .model("A4")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("red")
                .rimsInch(21)
                .capacity(1988)
                .engineType("GAS")
                .power(211)
                .torque(422)
                .avgFuelConsumption(7.4F)
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
                .basicPrice(new BigDecimal(685))
                .photoNames(List.of("car5.webp"))
                .build();
        VehicleEntity vehicle6 = VehicleEntity.builder()
                .id(6)
                .brand("BMW")
                .model("5")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("white")
                .rimsInch(21)
                .capacity(1988)
                .engineType("GAS")
                .power(211)
                .torque(422)
                .avgFuelConsumption(10.5F)
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
                .basicPrice(new BigDecimal(642))
                .photoNames(List.of("car6.webp"))
                .build();
        vehicleRepository.save(vehicle);
        vehicleRepository.save(vehicle2);
        vehicleRepository.save(vehicle3);
        vehicleRepository.save(vehicle4);
        vehicleRepository.save(vehicle5);
        vehicleRepository.save(vehicle6);
    }
}
