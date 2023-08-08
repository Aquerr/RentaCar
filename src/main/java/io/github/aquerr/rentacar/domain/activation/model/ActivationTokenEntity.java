package io.github.aquerr.rentacar.domain.activation.model;

import io.github.aquerr.rentacar.application.security.AccessTokenGenerator;
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
@Table(name = "account_activation_token")
public class ActivationTokenEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "credentials_id", nullable = false)
    private Long credentialsId;

    @Column(name = "token", nullable = false, unique = true, length = AccessTokenGenerator.ACTIVATION_TOKEN_LENGTH)
    private String token;

    @Column(name = "expiration_date_time", nullable = false)
    private ZonedDateTime expirationDate;

    @Column(name = "used", nullable = false, unique = false, columnDefinition = "tinyint(1) default 0")
    private boolean used;

    @Column(name = "valid", nullable = false, unique = false)
    private boolean valid;
}
