package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Long>
{
    UserCredentialsEntity findByUsername(String username);

    UserCredentialsEntity findByEmail(String email);

    UserCredentialsEntity findByUsernameOrEmail(String username, String email);

    @Query(nativeQuery = true, value = "SELECT * FROM user_credentials " +
            "WHERE user_credentials.user_id IN " +
            "(SELECT user_id FROM user_credentials WHERE user_credentials.activated = false AND user_credentials.user_id NOT IN (SELECT user_id FROM challenge_token WHERE challenge_token.user_id = user_credentials.user_id and challenge_token.operation_type = 'ACCOUNT_ACTIVATION') " +
            "UNION (SELECT user_id FROM challenge_token WHERE challenge_token.user_id = user_credentials.user_id AND challenge_token.operation_type = 'ACCOUNT_ACTIVATION' AND challenge_token.expiration_date_time < :dateBefore));")
    List<UserCredentialsEntity> findAllNotActivatedUserCredentialsBefore(@Param("dateBefore") OffsetDateTime dateBefore);

    @Modifying
    @Query("UPDATE UserCredentialsEntity credentials SET credentials.password = :newPassword WHERE credentials.userId = :userId")
    void updateByUserIdSetPassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);
}
