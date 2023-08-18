package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import io.github.aquerr.rentacar.repository.ProfileRepository;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import io.github.aquerr.rentacar.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    protected UserCredentialsRepository userCredentialsRepository;
    @Autowired
    protected ProfileRepository profileRepository;
    @Autowired
    protected VehicleRepository vehicleRepository;

    @BeforeEach
    public void setUp()
    {
        prepareUsers();
        prepareUserProfiles();
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

    protected void prepareUserProfiles()
    {
        Long credentialsId = userCredentialsRepository.findByUsername(USERNAME).getId();
        UserProfileEntity profileEntity = UserProfileEntity.builder()
                .credentialsId(credentialsId)
                .email(EMAIL)
                .city("TestCity")
                .birthDate(LocalDate.of(2001, 11, 4))
                .firstName("FirstName")
                .lastName("LastName")
                .build();
        profileRepository.save(profileEntity);
    }

    protected void prepareUsers()
    {
        UserCredentialsEntity userCredentials = new UserCredentialsEntity(null, USERNAME, EMAIL, "pass", Set.of());
        userCredentialsRepository.save(userCredentials);
    }
}
