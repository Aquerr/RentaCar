package io.github.aquerr.rentacar.application.security.challengetoken.model;

import io.github.aquerr.rentacar.application.security.challengetoken.ChallengeTokenGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "challenge_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChallengeTokenEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "operation_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OperationType operationType;

    @Column(name = "token", nullable = false, unique = true, length = ChallengeTokenGenerator.CHALLENGE_TOKEN_LENGTH)
    private String token;

    @CreationTimestamp
    @Column(name = "created_date_time", nullable = false)
    private OffsetDateTime createdDate;

    @Column(name = "expiration_date_time", nullable = false)
    private OffsetDateTime expirationDate;

    @Column(name = "used", nullable = false, unique = false, columnDefinition = "BOOLEAN default 0")
    private boolean used;
}
