package io.github.aquerr.rentacar.repository.mfa;

import io.github.aquerr.rentacar.application.security.mfa.model.UserMfaSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMfaSettingsRepository extends JpaRepository<UserMfaSettingsEntity, Long>
{
    Optional<UserMfaSettingsEntity> findByUserId(long userId);

    @Modifying
    @Query("DELETE from UserMfaSettingsEntity entity WHERE entity.userId = :userId")
    void deleteByUserId(@Param("userId") long userId);
}
