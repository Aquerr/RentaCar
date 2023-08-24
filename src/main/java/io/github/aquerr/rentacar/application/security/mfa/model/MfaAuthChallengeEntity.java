package io.github.aquerr.rentacar.application.security.mfa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Table(name = "mfa_auth_challenge")
@Data
public class MfaAuthChallengeEntity
{
    @Id
    @Column(name = "challenge", unique = true, nullable = false)
    private String challenge;

    @Column(name = "credentials_id", unique = true, nullable = false)
    private Long credentialsId;

    @Column(name = "expiration_date_time", nullable = false)
    private ZonedDateTime expirationDateTime;
}
