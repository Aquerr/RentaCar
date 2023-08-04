package io.github.aquerr.rentacar.domain.pickup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "vehicle_pickup_location")
@Entity
public class VehiclePickupLocationEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "city", unique = false, nullable = false)
    private String city;

    @Column(name = "lang_code", unique = false, nullable = false, length = 2, columnDefinition = "CHAR(2)")
    private String langCode;

    @Column(name = "latitude", unique = false, nullable = false, length = 7)
    private float latitude;

    @Column(name = "longitude", unique = false, nullable = false, length = 7)
    private float longitude;
}
