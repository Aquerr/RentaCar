package io.github.aquerr.rentacar.domain.vehicle.dto;

import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleCategory;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleEngine;
import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleTransmission;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class VehicleFullData {
    Integer id;
    String brand;
    String model;
    LocalDate productionYear;
    Integer seatsAmount;
    Body body;
    Engine engine;
    Equipment equipment;
    BigDecimal basicPrice;
    VehicleCategory category;
    List<byte[]> photos;

    @Value
    @Builder
    public static class Body {
        String color;
        Integer rimsInch;
    }

    @Value
    @Builder
    public static class Engine {
        Integer capacity;
        VehicleEngine type;
        Integer power;
        Integer torque;
        Float avgFuelConsumption;
        VehicleTransmission transmission;
    }

    @Value
    @Builder
    public static class Equipment {
        boolean ac;
        boolean frontPDC;
        boolean rearPDC;
        boolean bluetooth;
        boolean ledFrontLights;
        boolean xenonFrontLights;
        boolean ledRearLights;
        boolean leatherSeats;
        boolean multifunctionalSteeringWheel;
    }
}
