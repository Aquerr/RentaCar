package io.github.aquerr.rentacar.repository.mfa;

import io.github.aquerr.rentacar.application.security.mfa.model.UserMfaTotpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMfaTotpEntityRepository extends JpaRepository<UserMfaTotpEntity, Long>
{
    Optional<UserMfaTotpEntity> findByCredentialsId(Long id);


    @Modifying
    @Query("DELETE from UserMfaTotpEntity entity WHERE entity.credentialsId = :credentialsId")
    void deleteByCredentialsId(@Param("credentialsId") long credentialsId);
}
