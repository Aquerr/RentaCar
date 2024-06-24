package io.github.aquerr.rentacar.bootstrap;

import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.domain.user.dto.UserRegistrationParams;
import io.github.aquerr.rentacar.domain.user.model.Authority;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        vehicleRepository.deleteAll();

        createDummyUsers();
        createDummyVehicles();
    }

    private void createDummyUsers()
    {
        UserCredentialsEntity credentials1 = UserCredentialsEntity.builder()
                .username("test_user")
                .email("test_email@test.com")
                .password(passwordEncoder.encode("test_pass"))
                .activated(true)
                .locked(false)
                .build();

        UserProfileEntity profile1 = UserProfileEntity.builder()
                .firstName("Tester")
                .lastName("Testowski")
                .phoneNumber("999999999")
                .contactEmail("test_email@test.com")
                .birthDate(LocalDate.of(1999, 6, 15))
                .city("Testów")
                .zipCode("15551")
                .street("Wymyślna 42")
                .iconName("photo.jpg")
                .build();

        UserEntity userEntity1 = UserEntity.builder()
                .credentials(credentials1)
                .profile(profile1)
                .build();
        credentials1.setUser(userEntity1);
        profile1.setUser(userEntity1);

        userEntity1 = userRepository.save(userEntity1);

        log.info("Created dummy verified profile: {}", userEntity1);

        UserCredentialsEntity adminCredentials1 = UserCredentialsEntity.builder()
                .username("test_admin")
                .email("admin_email@test.com")
                .password(passwordEncoder.encode("test_admin"))
                .activated(true)
                .locked(false)
                .build();

        UserProfileEntity adminProfile1 = UserProfileEntity.builder()
                .firstName("Admin")
                .lastName("Testowski")
                .phoneNumber("111222333")
                .contactEmail("admin_email@test.com")
                .birthDate(LocalDate.of(1986, 2, 5))
                .city("Testów")
                .zipCode("14239")
                .street("Wymyślna 92")
                .iconName("photo.jpg")
                .build();

        UserEntity adminUser1 = UserEntity.builder()
                .credentials(adminCredentials1)
                .authorities(Set.of(Authority.ADD_VEHICLE.getAuthority(), Authority.VIEW_CAR_LOCATION.getAuthority(), Authority.VIEW_ADMIN_PANEL.getAuthority(), Authority.REMOVE_VEHICLE.getAuthority(), Authority.GET_ALL_RESERVATIONS.getAuthority()))
                .profile(adminProfile1)
                .build();

        adminCredentials1.setUser(adminUser1);
        adminProfile1.setUser(adminUser1);

        adminUser1 = userRepository.save(adminUser1);

        log.info("Created dummy verified admin profile: {}", adminUser1);

        UserRegistrationParams userRegistrationParams = new UserRegistrationParams("tester2", "testing@test.com", "testujemy");
        userService.register(userRegistrationParams);
    }

    private void createDummyVehicles()
    {
        VehicleEntity vehicle = VehicleEntity.builder()
                .id(1)
                .brand("TOYOTA")
                .model("YARIS")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("#ffffff")
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
                .cruiseControl(true)
                .category("C")
                .basicPrice(new BigDecimal(245))
                .photoNames(List.of("car1.webp"))
                .build();
        log.info("Created dummy vehicle: {}", vehicle);
        VehicleEntity vehicle2 = VehicleEntity.builder()
                .id(2)
                .brand("TOYOTA")
                .model("AVENSIS")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("#ffffff")
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
                .cruiseControl(false)
                .category("B")
                .basicPrice(new BigDecimal(145))
                .photoNames(List.of("car2.webp"))
                .build();
        log.info("Created dummy vehicle: {}", vehicle2);
        VehicleEntity vehicle3 = VehicleEntity.builder()
                .id(3)
                .brand("LEXUS")
                .model("MODEL")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("#ffffff")
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
                .cruiseControl(true)
                .category("A")
                .basicPrice(new BigDecimal(545))
                .photoNames(List.of("car3.webp"))
                .build();
        log.info("Created dummy vehicle: {}", vehicle3);
        VehicleEntity vehicle4 = VehicleEntity.builder()
                .id(4)
                .brand("AUDI")
                .model("A6")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("#bbbbbb")
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
                .cruiseControl(true)
                .category("A")
                .basicPrice(new BigDecimal(245))
                .photoNames(List.of("car4.webp"))
                .build();
        log.info("Created dummy vehicle: {}", vehicle4);
        VehicleEntity vehicle5 = VehicleEntity.builder()
                .id(5)
                .brand("AUDI")
                .model("A4")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("#ffffff")
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
                .cruiseControl(false)
                .category("A")
                .basicPrice(new BigDecimal(685))
                .photoNames(List.of("car5.webp"))
                .build();
        log.info("Created dummy vehicle: {}", vehicle5);
        VehicleEntity vehicle6 = VehicleEntity.builder()
                .id(6)
                .brand("BMW")
                .model("5")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .color("#bbbbbb")
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
                .cruiseControl(true)
                .category("A")
                .basicPrice(new BigDecimal(642))
                .photoNames(List.of("car6.webp"))
                .build();
        log.info("Created dummy vehicle: {}", vehicle6);
        vehicleRepository.save(vehicle);
        vehicleRepository.save(vehicle2);
        vehicleRepository.save(vehicle3);
        vehicleRepository.save(vehicle4);
        vehicleRepository.save(vehicle5);
        vehicleRepository.save(vehicle6);
    }
}
