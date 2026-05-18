package io.github.aquerr.rentacar.application.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "invalid_jwt_token")
public class InvalidJwtTokenEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "jwt", unique = true, nullable = false)
    private String jwt;

    @Column(name = "invalidated_date_time", nullable = false)
    private ZonedDateTime invalidatedDateTime;

    @Column(name = "expiration_date_time", nullable = false)
    private ZonedDateTime expirationDateTime;
}
