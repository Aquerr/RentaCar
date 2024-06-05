package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationTokenEntity, Long>
{
    Optional<ActivationTokenEntity> findByToken(String token);

    List<ActivationTokenEntity> findAllByUserIdIn(List<Long> userIds);

    @Modifying
    @Query("update PasswordResetTokenEntity activationToken SET activationToken.used = true WHERE activationToken.userId = :userId")
    void invalidateOldActivationTokens(@Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM PasswordResetTokenEntity activationToken WHERE activationToken.expirationDate < :dateTime")
    void deleteAllByExpirationDateTimeBefore(@Param("dateTime")ZonedDateTime beforeDateTime);
}
