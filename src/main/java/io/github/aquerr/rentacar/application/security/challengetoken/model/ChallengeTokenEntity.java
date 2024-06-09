package io.github.aquerr.rentacar.application.security.challengetoken.model;

import io.github.aquerr.rentacar.application.security.challengetoken.ChallengeTokenGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "challenge_token")
@Builder(toBuilder = true)
public record ChallengeTokenEntity(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,

        @Column(name = "user_id", nullable = false)
        Long userId,

        @Column(name = "operation_type", nullable = false)
        OperationType operationType,

        @Column(name = "token", nullable = false, unique = true, length = ChallengeTokenGenerator.CHALLENGE_TOKEN_LENGTH)
        String token,

        @CreationTimestamp
        @Column(name = "created_date_time", nullable = false)
        ZonedDateTime createdDate,

        @Column(name = "expiration_date_time", nullable = false)
        ZonedDateTime expirationDate,

        @Column(name = "used", nullable = false, unique = false, columnDefinition = "BOOLEAN default 0")
        boolean used
)
{

}
