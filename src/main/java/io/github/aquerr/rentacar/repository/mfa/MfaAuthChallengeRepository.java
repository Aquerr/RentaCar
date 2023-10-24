package io.github.aquerr.rentacar.repository.mfa;

import io.github.aquerr.rentacar.application.security.mfa.model.MfaAuthChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MfaAuthChallengeRepository extends JpaRepository<MfaAuthChallengeEntity, String>
{

}
