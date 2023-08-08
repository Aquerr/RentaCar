package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.application.security.InvalidJwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvalidJwtTokenRepository extends JpaRepository<InvalidJwtTokenEntity, Integer>
{
    @Query(value = "DELETE FROM InvalidJwtTokenEntity invalidJwtTokenEntity WHERE invalidJwtTokenEntity.expirationDateTime < NOW()")
    void deleteAllByExpirationDateTimeBeforeNow();
}
