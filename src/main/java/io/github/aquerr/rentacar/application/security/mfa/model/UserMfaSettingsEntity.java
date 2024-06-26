package io.github.aquerr.rentacar.application.security.mfa.model;

import io.github.aquerr.rentacar.application.security.mfa.MfaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_mfa_settings")
@Data
public class UserMfaSettingsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "mfa_type", unique = false, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MfaType mfaType;

    @Column(name = "verified", unique = false, nullable = false, columnDefinition = "BOOLEAN default 0")
    private boolean verified;

    @Column(name = "verified_date_time", nullable = true)
    private ZonedDateTime verifiedDate;
}
