package io.github.aquerr.rentacar.repository.mfa;

import io.github.aquerr.rentacar.application.security.mfa.model.MfaRecoveryCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MfaRecoveryCodesRepository extends JpaRepository<MfaRecoveryCodeEntity, Long>
{

}
