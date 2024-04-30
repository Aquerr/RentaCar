package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Long>
{
    UserCredentialsEntity findByUsername(String username);

    UserCredentialsEntity findByEmail(String email);

    UserCredentialsEntity findByUsernameOrEmail(String username, String email);

    @Query(nativeQuery = true, value = "SELECT * FROM user_credentials " +
            "WHERE user_credentials.id IN " +
            "(SELECT id FROM user_credentials WHERE user_credentials.activated = false AND user_credentials.id NOT IN (SELECT credentials_id FROM account_activation_token WHERE account_activation_token.credentials_id = user_credentials.id) " +
            "UNION (SELECT credentials_id FROM account_activation_token WHERE account_activation_token.credentials_id = user_credentials.id AND account_activation_token.expiration_date_time < :dateBefore));")
    List<UserCredentialsEntity> findAllNotActivatedUserCredentialsBefore(@Param("dateBefore") ZonedDateTime dateBefore);

    @Modifying
    @Query("UPDATE UserCredentialsEntity credentials SET credentials.password = :newPassword WHERE credentials.id = :credentialsId")
    void updateByCredentialsIdSetPassword(@Param("credentialsId") Long credentialsId, @Param("newPassword") String newPassword);
}
