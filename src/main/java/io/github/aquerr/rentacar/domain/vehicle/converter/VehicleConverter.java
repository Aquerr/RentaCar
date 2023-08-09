package io.github.aquerr.rentacar.domain.vehicle.converter;

import io.github.aquerr.rentacar.domain.vehicle.VehicleEntity;
import io.github.aquerr.rentacar.domain.vehicle.VehicleImagePopulator;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleBasicData;
import io.github.aquerr.rentacar.domain.vehicle.dto.VehicleFullData;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleCategory;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleEngine;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleTransmission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class VehicleConverter {

    private final VehicleImagePopulator vehicleImagePopulator;

    public VehicleBasicData toBasicData(VehicleEntity vehicleEntity) {
        if (vehicleEntity == null) {
            return null;
        }

        VehicleBasicData.VehicleBasicDataBuilder builder = VehicleBasicData.builder()
                .id(vehicleEntity.getId())
                .brand(vehicleEntity.getBrand())
                .model(vehicleEntity.getModel())
                .engine(VehicleBasicData.Engine.builder()
                        .type(VehicleEngine.valueOf(vehicleEntity.getEngineType()))
                        .avgFuelConsumption(vehicleEntity.getAvgFuelConsumption())
                        .build())
                .equipment(VehicleBasicData.Equipment.builder()
                        .ac(vehicleEntity.isAc())
                        .ledFrontLights(vehicleEntity.isLedFrontLights())
                        .ledRearLights(vehicleEntity.isLedRearLights())
                        .leatherSeats(vehicleEntity.isLeatherSeats())
                        .multifunctionalSteeringWheel(vehicleEntity.isMultifunctionalSteeringWheel())
                        .build())
                .basicPrice(vehicleEntity.getBasicPrice());

        vehicleImagePopulator.populate(builder, vehicleEntity.getPhotoNames());

        return builder.build();
    }

    public VehicleFullData toFullData(VehicleEntity vehicleEntity) {
        if (vehicleEntity == null) {
            return null;
        }

        VehicleFullData.VehicleFullDataBuilder builder = VehicleFullData.builder()
                .id(vehicleEntity.getId())
                .brand(vehicleEntity.getBrand())
                .model(vehicleEntity.getModel())
                .productionYear(vehicleEntity.getProductionYear())
                .seatsAmount(vehicleEntity.getSeatsAmount())
                .body(VehicleFullData.Body.builder()
                        .color(vehicleEntity.getColor())
                        .rimsInch(vehicleEntity.getRimsInch())
                        .build())
                .engine(VehicleFullData.Engine.builder()
                        .capacity(vehicleEntity.getCapacity())
                        .type(VehicleEngine.valueOf(vehicleEntity.getEngineType()))
                        .power(vehicleEntity.getPower())
                        .torque(vehicleEntity.getTorque())
                        .avgFuelConsumption(vehicleEntity.getAvgFuelConsumption())
                        .transmission(VehicleTransmission.valueOf(vehicleEntity.getTransmission()))
                        .build())
                .equipment(VehicleFullData.Equipment.builder()
                        .ac(vehicleEntity.isAc())
                        .frontPDC(vehicleEntity.isFrontPDC())
                        .rearPDC(vehicleEntity.isRearPDC())
                        .ledFrontLights(vehicleEntity.isLedFrontLights())
                        .ledRearLights(vehicleEntity.isLedRearLights())
                        .xenonFrontLights(vehicleEntity.isXenonFrontLights())
                        .bluetooth(vehicleEntity.isBluetooth())
                        .leatherSeats(vehicleEntity.isLeatherSeats())
                        .multifunctionalSteeringWheel(vehicleEntity.isMultifunctionalSteeringWheel())
                        .build())
                .basicPrice(vehicleEntity.getBasicPrice())
                .category(VehicleCategory.valueOf(vehicleEntity.getCategory()));

        vehicleImagePopulator.populate(builder, vehicleEntity.getPhotoNames());

        return builder.build();
    }
}
