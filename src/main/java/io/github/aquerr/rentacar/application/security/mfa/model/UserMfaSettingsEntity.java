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

@Entity
@Table(name = "user_mfa_settings")
@Data
public class UserMfaSettingsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "credentials_id", unique = true, nullable = false)
    private Long credentialsId;

    @Column(name = "mfa_type", unique = false, nullable = false, columnDefinition = "INTEGER")
    @Enumerated(value = EnumType.STRING)
    private MfaType mfaType;

    @Column(name = "verified", unique = false, nullable = false, columnDefinition = "BOOLEAN default 0")
    private boolean verified;
}
