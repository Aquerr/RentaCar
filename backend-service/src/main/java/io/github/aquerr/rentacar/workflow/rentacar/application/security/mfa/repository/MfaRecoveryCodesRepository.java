package io.github.aquerr.rentacar.workflow.rentacar.application.security.mfa.repository;

import io.github.aquerr.rentacar.workflow.rentacar.application.security.mfa.model.MfaRecoveryCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MfaRecoveryCodesRepository extends JpaRepository<MfaRecoveryCodeEntity, Long>
{

}
