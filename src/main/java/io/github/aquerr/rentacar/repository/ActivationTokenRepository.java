package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.activation.model.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long>
{
    Optional<ActivationToken> findByToken(String token);
}
