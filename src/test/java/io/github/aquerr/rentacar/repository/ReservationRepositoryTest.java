package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.reservation.model.ReservationEntity;
import io.github.aquerr.rentacar.domain.reservation.model.ReservationStatus;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationRepositoryTest extends BaseDBIntegrationTest
{
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @BeforeEach
    public void setUp()
    {
        prepareUsers();
        prepareVehicles();
        prepareVehicleReservations();
    }

    @Test
    void shouldSaveReservation()
    {
        assertThat(reservationRepository.findAll()).hasSize(1);

        UserEntity userEntity = userRepository.findByCredentials_Username(USERNAME);
        VehicleEntity vehicle = vehicleRepository.findByBrandAndModel("TOYOTA", "YARIS").get(0);
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .vehicle(vehicle)
                .userId(userEntity.getId())
                .vehicleId(vehicle.getId())
                .status(ReservationStatus.PAYMENT_COMPLETED.getStatus())
                .dateFrom(LocalDate.of(2023, 9, 15))
                .dateTo(LocalDate.of(2023, 10, 17))
                .build();

        reservationRepository.save(reservationEntity);

        assertThat(reservationRepository.findAll()).hasSize(2);
    }

    @Test
    void shouldFindAllReservations()
    {
        assertThat(reservationRepository.findAll()).isNotEmpty();
    }

    @Test
    void shouldDeleteAllReservations()
    {
        UserEntity userEntity = userRepository.findByCredentials_Username(USERNAME);
        VehicleEntity vehicle = vehicleRepository.findByBrandAndModel("TOYOTA", "YARIS").get(0);
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .vehicle(vehicle)
                .userId(userEntity.getId())
                .vehicleId(vehicle.getId())
                .status(ReservationStatus.PAYMENT_COMPLETED.getStatus())
                .dateFrom(LocalDate.of(2023, 9, 15))
                .dateTo(LocalDate.of(2023, 10, 17))
                .build();

        reservationRepository.save(reservationEntity);

        assertThat(reservationRepository.findAll()).isNotEmpty();
        reservationRepository.deleteAll();
        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @MethodSource(value = "reservationDatesToCheck")
    @ParameterizedTest
    void shouldFindAllNotAvailableVehiclesBetweenDates(LocalDate from, LocalDate to, boolean findsNotAvailableVehicle)
    {
        List<Integer> vehicleIds = reservationRepository.findAllNotAvailableVehiclesBetweenDates(from, to);
        if (findsNotAvailableVehicle)
        {
            assertThat(vehicleIds).isNotEmpty();
        }
        else
        {
            assertThat(vehicleIds).isEmpty();
        }
    }

    private static Object[][] reservationDatesToCheck()
    {
        return new Object[][] {
                {LocalDate.of(2023, 4, 8), LocalDate.of(2023, 4, 10), true},
                {LocalDate.of(2023, 4, 6), LocalDate.of(2023, 4, 11), true},
                {LocalDate.of(2023, 4, 6), LocalDate.of(2023, 4, 7), true},
                {LocalDate.of(2023, 4, 14), LocalDate.of(2023, 4, 16), true},
                {LocalDate.of(2023, 4, 5), LocalDate.of(2023, 4, 6), false},
                {LocalDate.of(2023, 4, 15), LocalDate.of(2023, 4, 18), false}
        };
    }

    private void prepareVehicleReservations()
    {
        UserEntity userEntity = userRepository.findByCredentials_Username(USERNAME);
        VehicleEntity vehicle = vehicleRepository.findByBrandAndModel("TOYOTA", "YARIS").get(0);

        ReservationEntity reservationEntity = ReservationEntity.builder()
                .userId(userEntity.getId())
                .vehicle(vehicle)
                .vehicleId(vehicle.getId())
                .dateFrom(LocalDate.of(2023, 4, 7))
                .dateTo(LocalDate.of(2023, 4, 14))
                .status(ReservationStatus.PAYMENT_COMPLETED.getStatus())
                .build();

        reservationRepository.save(reservationEntity);
    }
}