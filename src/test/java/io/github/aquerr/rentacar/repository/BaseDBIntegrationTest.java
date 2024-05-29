package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.dto.UserCredentials;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Transactional
public abstract class BaseDBIntegrationTest
{
    protected static final String USERNAME = "test";
    protected static final String EMAIL = "test@test.com";

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserCredentialsRepository userCredentialsRepository;
    @Autowired
    protected ProfileRepository profileRepository;
    @Autowired
    protected VehicleRepository vehicleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp()
    {
        prepareUsers();
        prepareVehicles();
    }

    protected void prepareVehicles()
    {
        VehicleEntity vehicle = VehicleEntity.builder()
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
                .cruiseControl(true)
                .category("C")
                .basicPrice(new BigDecimal(245))
                .photoNames(List.of("car1.webp"))
                .build();
        vehicleRepository.save(vehicle);
    }

    protected void prepareUsers()
    {
        UserCredentialsEntity userCredentialsEntity = UserCredentialsEntity.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(passwordEncoder.encode("pass"))
                .build();

        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .contactEmail(EMAIL)
                .city("TestCity")
                .birthDate(LocalDate.of(2001, 11, 4))
                .firstName("FirstName")
                .lastName("LastName")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .credentials(userCredentialsEntity)
                .authorities(Set.of())
                .profile(userProfileEntity)
                .build();

        userCredentialsEntity.setUser(userEntity);
        userProfileEntity.setUser(userEntity);

        userRepository.saveAllAndFlush(List.of(userEntity));
    }
}
