package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.password.model.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long>
{
    Optional<PasswordResetTokenEntity> findByToken(String token);

    List<PasswordResetTokenEntity> findAllByCredentialsIdIn(List<Long> credentialsIds);

    @Modifying
    @Query("update PasswordResetTokenEntity token SET token.used = true WHERE token.credentialsId = :credentialsId")
    void invalidateOldResetTokens(@Param("credentialsId") long credentialsId);

    @Modifying
    @Query(value = "DELETE FROM PasswordResetTokenEntity token WHERE token.expirationDate < :dateTime")
    void deleteAllByExpirationDateTimeBefore(@Param("dateTime") ZonedDateTime beforeDateTime);


    @Modifying
    @Query("UPDATE PasswordResetTokenEntity token SET token.used = TRUE WHERE token.token = :token")
    void updateByTokenSetUsedTrue(@Param("token") String token);
}
