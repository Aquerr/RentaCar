package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByCredentials_Username(String username);

    UserEntity findByCredentials_Email(String email);

    UserEntity findByCredentials_UsernameAndCredentials_Email(String username, String email);
}
