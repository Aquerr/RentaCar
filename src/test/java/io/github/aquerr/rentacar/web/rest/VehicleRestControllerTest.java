package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.vehicle.VehicleService;
import io.github.aquerr.rentacar.domain.vehicle.dto.AvailableVehiclesSearchParams;
import io.github.aquerr.rentacar.domain.vehicle.dto.AvailableVehiclesSearchResult;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleBasicData;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleFullData;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleCategory;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleEngine;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleTransmission;
import io.github.aquerr.rentacar.util.TestResourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VehicleRestController.class)
class VehicleRestControllerTest extends BaseRestIntegrationTest
{
    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private VehicleRestController vehicleRestController;
    private MockMvc mockMvc;

    @BeforeEach
    void setup()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(vehicleRestController)
                .build();
    }

    @Test
    void shouldGetVehicleFullDataReturnVehicleFullDataResponse() throws Exception
    {
        // given
        int vehicleId = 2;
        given(vehicleService.getVehicleFullData(vehicleId)).willReturn(VehicleFullData.builder()
                .id(2)
                .brand("TOYOTA")
                .model("AVENSIS")
                .productionYear(LocalDate.now())
                .seatsAmount(5)
                .engine(VehicleFullData.Engine.builder()
                        .type(VehicleEngine.GAS)
                        .power(211)
                        .torque(422)
                        .avgFuelConsumption(4.5F)
                        .transmission(VehicleTransmission.MANUAL)
                        .build())
                .body(VehicleFullData.Body.builder()
                        .color("red")
                        .rimsInch(21)
                        .build())
                        .equipment(VehicleFullData.Equipment.builder()
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
                                .build())
                        .category(VehicleCategory.B)
                .basicPrice(new BigDecimal(145))
                .photosUrls(List.of("http://localhost:8086/api/v1/assets/images/VEHICLE/car2.webp"))
                .build());

        String json = TestResourceUtils.loadMockJson("mock-json/get_vehicle_full_data.json");

        // when
        // then
        mockMvc.perform(request(HttpMethod.GET, "/api/v1/vehicles/full-data/{vehicleId}", vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/get_vehicle_full_data.json")));
    }

    @Test
    void shouldGetVehiclesAvailableReturnSearchVehicleBasicDataResponse() throws Exception
    {
        // given
        LocalDate from = LocalDate.of(2023, 8, 4);
        LocalDate to = LocalDate.of(2023, 8, 14);
        int page = 2;
        int size = 5;
        AvailableVehiclesSearchParams availableVehiclesSearchParams = AvailableVehiclesSearchParams.of(from, to, page, size);
        given(vehicleService.getVehiclesAvailable(availableVehiclesSearchParams)).willReturn(AvailableVehiclesSearchResult.of(
                List.of(VehicleBasicData.builder()
                                .id(1)
                                .brand("TOYOTA")
                                .model("AVENSIS")
                                .engine(VehicleBasicData.Engine.builder()
                                        .type(VehicleEngine.GAS)
                                        .avgFuelConsumption(4.5F)
                                        .build())
                                .equipment(VehicleBasicData.Equipment.builder()
                                        .ac(true)
                                        .ledFrontLights(true)
                                        .ledRearLights(false)
                                        .leatherSeats(true)
                                        .multifunctionalSteeringWheel(true)
                                        .build())
                                .basicPrice(new BigDecimal(145))
                                .photoUrl("http://localhost:8086/api/v1/assets/images/VEHICLE/car2.webp")
                                .build(),
                        VehicleBasicData.builder()
                                .id(2)
                                .brand("AUDI")
                                .model("A6")
                                .engine(VehicleBasicData.Engine.builder()
                                        .type(VehicleEngine.GAS)
                                        .avgFuelConsumption(8.5F)
                                        .build())
                                .equipment(VehicleBasicData.Equipment.builder()
                                        .ac(true)
                                        .ledFrontLights(true)
                                        .ledRearLights(false)
                                        .leatherSeats(true)
                                        .multifunctionalSteeringWheel(true)
                                        .build())
                                .basicPrice(new BigDecimal(145))
                                .photoUrl("http://localhost:8086/api/v1/assets/images/VEHICLE/car3.webp")
                                .build(),
                        VehicleBasicData.builder().id(3).build(),
                        VehicleBasicData.builder()
                                .id(3)
                                .brand("LEXUS")
                                .model("MODEL")
                                .engine(VehicleBasicData.Engine.builder()
                                        .type(VehicleEngine.DIESEL)
                                        .avgFuelConsumption(6.8F)
                                        .build())
                                .equipment(VehicleBasicData.Equipment.builder()
                                        .ac(true)
                                        .ledFrontLights(true)
                                        .ledRearLights(false)
                                        .leatherSeats(true)
                                        .multifunctionalSteeringWheel(true)
                                        .build())
                                .basicPrice(new BigDecimal(145))
                                .photoUrl("http://localhost:8086/api/v1/assets/images/VEHICLE/car1.webp")
                                .build()),
                3,
                1
        ));

        // when
        // then
        mockMvc.perform(request(HttpMethod.GET, "/api/v1/vehicles/available")
                        .queryParam("dateFrom", "2023-08-04")
                        .queryParam("dateTo", "2023-08-14")
                        .queryParam("page", "2")
                        .queryParam("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResourceUtils.loadMockJson("mock-json/search_vehicle_basic_data_response.json")));
    }
}