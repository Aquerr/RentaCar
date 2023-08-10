package io.github.aquerr.rentacar.domain.vehicle.dto;

import io.github.aquerr.rentacar.domain.vehicle.enums.VehicleEngine;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class VehicleBasicData
{
    Integer id;
    String brand;
    String model;
    Engine engine;
    Equipment equipment;
    BigDecimal basicPrice;
    String photoUrl;

    @Value
    @Builder
    public static class Engine {
        VehicleEngine type;
        Float avgFuelConsumption;
    }

    @Value
    @Builder
    public static class Equipment {
        boolean ac;
        boolean ledFrontLights;
        boolean ledRearLights;
        boolean leatherSeats;
        boolean multifunctionalSteeringWheel;
    }
}
