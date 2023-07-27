package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.model.RentaCarUserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<RentaCarUserCredentials, Long>
{
    RentaCarUserCredentials findByUsername(String username);

    RentaCarUserCredentials findByEmail(String email);

    RentaCarUserCredentials findByUsernameOrEmail(String username, String email);
}
