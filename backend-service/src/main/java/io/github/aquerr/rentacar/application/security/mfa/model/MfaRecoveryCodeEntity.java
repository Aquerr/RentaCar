package io.github.aquerr.rentacar.application.security.mfa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mfa_recovery_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MfaRecoveryCodeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mfa_recovery_codes_generator")
    @SequenceGenerator(name = "mfa_recovery_codes_generator", sequenceName = "mfa_recovery_codes_seq", allocationSize = 6)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long user_id;

    @Column(name = "recovery_code")
    private String recoveryCode;
}
