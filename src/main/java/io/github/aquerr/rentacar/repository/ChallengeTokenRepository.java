package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface ChallengeTokenRepository extends JpaRepository<ChallengeTokenEntity, Long>
{
    Optional<ChallengeTokenEntity> findByTokenAndOperationType(String token, OperationType operationType);

//    List<ChallengeTokenEntity> findAllByUserIdIn(List<Long> userIds);

    @Modifying
    @Query("update ChallengeTokenEntity challengeToken SET challengeToken.used = true WHERE challengeToken.userId = :userId")
    void invalidateOldChallengeTokens(@Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM ChallengeTokenEntity challengeToken WHERE challengeToken.expirationDate < :dateTime")
    void deleteAllByExpirationDateTimeBefore(@Param("dateTime") ZonedDateTime beforeDateTime);


    @Modifying
    @Query("UPDATE ChallengeTokenEntity token SET token.used = TRUE WHERE token.token = :token")
    void updateByTokenSetUsedTrue(@Param("token") String token);
}
