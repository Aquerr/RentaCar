package io.github.aquerr.rentacar.domain.user.password.model;

import io.github.aquerr.rentacar.application.security.AccessTokenGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "password_reset_token")
public class PasswordResetTokenEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "credentials_id", nullable = false)
    private Long credentialsId;

    @Column(name = "token", nullable = false, unique = true, length = AccessTokenGenerator.PASSWORD_RESET_TOKEN_LENGTH)
    private String token;

    @Column(name = "expiration_date_time", nullable = false)
    private ZonedDateTime expirationDate;

    @Column(name = "used", nullable = false, unique = false, columnDefinition = "BOOLEAN default 0")
    private boolean used;
}
