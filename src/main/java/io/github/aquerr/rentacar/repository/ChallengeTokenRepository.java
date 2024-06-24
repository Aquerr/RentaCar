package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.application.security.challengetoken.model.ChallengeTokenEntity;
import io.github.aquerr.rentacar.application.security.challengetoken.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ChallengeTokenRepository extends JpaRepository<ChallengeTokenEntity, Long>
{
    Optional<ChallengeTokenEntity> findByTokenAndOperationType(String token, OperationType operationType);

    List<ChallengeTokenEntity> findAllByUserIdIn(List<Long> userIds);

    Optional<ChallengeTokenEntity> findByUserIdAndToken(long userId, String token);

    @Modifying
    void deleteByUserIdAndToken(long userId, String token);

    @Modifying
    @Query("update ChallengeTokenEntity challengeToken SET challengeToken.used = true WHERE challengeToken.userId = :userId and challengeToken.operationType = :operationType")
    void invalidateOldChallengeTokens(@Param("userId") long userId, @Param("operationType") OperationType operationType);

    @Modifying
    @Query(value = "DELETE FROM ChallengeTokenEntity challengeToken WHERE challengeToken.expirationDate < :dateTime")
    void deleteAllByExpirationDateTimeBefore(@Param("dateTime") OffsetDateTime beforeDateTime);


    @Modifying
    @Query("UPDATE ChallengeTokenEntity token SET token.used = TRUE WHERE token.token = :token and token.operationType = :operationType")
    void updateByTokenSetUsedTrue(@Param("token") String token, @Param("operationType") OperationType operationType);

    @Modifying
    void deleteAllByUserIdInAndOperationType(List<Long> userIds, OperationType operationType);
}
