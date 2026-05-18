package io.github.aquerr.rentacar.domain.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    LocalDate productionYear;
    Integer seatsAmount;
    Body body;
    Engine engine;
    Equipment equipment;
    BigDecimal basicPrice;
    VehicleCategory category;
    List<String> photosUrls;

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
        boolean cruiseControl;
    }
}
