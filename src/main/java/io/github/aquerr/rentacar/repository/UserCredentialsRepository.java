package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Long>
{
    UserCredentialsEntity findByUsername(String username);

    UserCredentialsEntity findByEmail(String email);

    UserCredentialsEntity findByUsernameOrEmail(String username, String email);
}
