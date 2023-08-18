package io.github.aquerr.rentacar.domain.vehicle;

import io.github.aquerr.rentacar.domain.vehicle.converter.VehicleConverter;
import io.github.aquerr.rentacar.domain.vehicle.dto.AvailableVehiclesSearchParams;
import io.github.aquerr.rentacar.domain.vehicle.dto.AvailableVehiclesSearchResult;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleBasicData;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleFullData;
import io.github.aquerr.rentacar.repository.ReservationRepository;
import io.github.aquerr.rentacar.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest
{
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleConverter vehicleConverter;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void shouldGetVehicleFullDataReturnVehicleEntityMappedToVehicleFullData()
    {
        // given
        int vehicleId = 1;
        given(vehicleRepository.findById(vehicleId)).willReturn(Optional.of(VehicleEntity.builder().id(vehicleId).build()));
        given(vehicleConverter.toFullData(any())).willReturn(VehicleFullData.builder().id(vehicleId).build());

        // when
        VehicleFullData vehicleFullData = vehicleService.getVehicleFullData(vehicleId);

        // then
        assertThat(vehicleFullData).isEqualTo(VehicleFullData.builder().id(vehicleId).build());
    }

    @Test
    void shouldGetVehiclesAvailableReturnBasicVehicleDataAvailableBetweenDates()
    {
        // given
        List<Integer> availableVehiclesIds = List.of(4, 5);
        LocalDate from = LocalDate.of(2023, 4, 11);
        LocalDate to = LocalDate.of(2023, 4, 16);
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        AvailableVehiclesSearchParams searchParams = AvailableVehiclesSearchParams.of(from, to, page, size);

        Page<VehicleEntity> availableVehiclesPage = new PageImpl<>(availableVehiclesIds.stream().map(id -> VehicleEntity.builder().id(id).build()).toList());

        given(vehicleRepository.findAllIds()).willReturn(List.of(1, 2, 3, 4, 5, 6));
        given(reservationRepository.findAllNotAvailableVehiclesBetweenDates(from, to)).willReturn(List.of(1, 2, 3, 6));
        given(vehicleRepository.findAllByIdIn(availableVehiclesIds, pageRequest))
                .willReturn(availableVehiclesPage);
        given(vehicleConverter.toBasicData(any())).willReturn(VehicleBasicData.builder().build());

        // when
        AvailableVehiclesSearchResult searchResult = vehicleService.getVehiclesAvailable(searchParams);

        // then
        assertThat(searchResult.getVehicles()).hasSize(2);
        assertThat(searchResult.getTotalPages()).isEqualTo(1);
        assertThat(searchResult.getTotalElements()).isEqualTo(2);
    }
}