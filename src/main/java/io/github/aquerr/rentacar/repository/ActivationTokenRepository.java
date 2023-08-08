package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.activation.model.ActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationTokenEntity, Long>
{
    Optional<ActivationTokenEntity> findByToken(String token);

    List<ActivationTokenEntity> findAllByCredentialsIdIn(List<Long> credentialsIds);

    void deleteActivationTokenEntitiesByExpirationDateBefore(ZonedDateTime beforeDateTime);

    @Query(nativeQuery = true, value = "DELETE FROM account_activation_token WHERE DATEADD(day, 14, account_activation_token.expiration_date_time) < NOW()")
    void deleteAllByExpirationDateTime2WeeksBeforeNow();
}
