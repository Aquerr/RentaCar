package io.github.aquerr.rentacar.workflow.rentacar.application.security.repository;

import io.github.aquerr.rentacar.workflow.rentacar.application.security.InvalidJwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InvalidJwtTokenRepository extends JpaRepository<InvalidJwtTokenEntity, Integer>
{
    @Modifying
    @Query(value = "DELETE FROM InvalidJwtTokenEntity invalidJwtTokenEntity WHERE invalidJwtTokenEntity.expirationDateTime < NOW()")
    void deleteAllByExpirationDateTimeBeforeNow();
}
