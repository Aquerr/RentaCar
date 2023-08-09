package io.github.aquerr.rentacar.domain.vehicle;

import io.github.aquerr.rentacar.application.persistance.converter.SemicolonSeparatedStringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "vehicle")
public class VehicleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "brand", unique = false, nullable = false)
    private String brand;

    @Column(name = "model", unique = false, nullable = false)
    private String model;

    @Column(name = "production_year", unique = false, nullable = false)
    private LocalDate productionYear;

    @Column(name = "seats_amount", unique = false, nullable = false)
    private Integer seatsAmount;

    @Column(name = "color", unique = false, nullable = false)
    private String color;

    @Column(name = "rims_inch", unique = false, nullable = false)
    private Integer rimsInch;

    @Column(name = "capacity", unique = false, nullable = false)
    private Integer capacity;

    @Column(name = "engine_type", unique = false, nullable = false)
    private String engineType;

    @Column(name = "power", unique = false, nullable = false)
    private Integer power;

    @Column(name = "torque", unique = false, nullable = false)
    private Integer torque;

    @Column(name = "avg_fuel_consumption", unique = false, nullable = false)
    private Float avgFuelConsumption;

    @Column(name = "transmission", unique = false, nullable = false)
    private String transmission;

    @Column(name = "ac", unique = false, nullable = false)
    private boolean ac;

    @Column(name = "front_pdc", unique = false, nullable = false)
    private boolean frontPDC;

    @Column(name = "rear_pdc", unique = false, nullable = false)
    private boolean rearPDC;

    @Column(name = "bluetooth", unique = false, nullable = false)
    private boolean bluetooth;

    @Column(name = "led_front_lights", unique = false, nullable = false)
    private boolean ledFrontLights;

    @Column(name = "xenon_front_lights", unique = false, nullable = false)
    private boolean xenonFrontLights;

    @Column(name = "led_rear_lights", unique = false, nullable = false)
    private boolean ledRearLights;

    @Column(name = "leather_seats", unique = false, nullable = false)
    private boolean leatherSeats;

    @Column(name = "multifunctional_steering_wheel", unique = false, nullable = false)
    private boolean multifunctionalSteeringWheel;

    @Column(name = "category", unique = false, nullable = false)
    private String category;

    @Column(name = "basic_price", unique = false, nullable = false)
    private BigDecimal basicPrice;

    @Convert(converter = SemicolonSeparatedStringListConverter.class)
    @Column(name = "photo_names", unique = false, nullable = false)
    private List<String> photoNames;

}
