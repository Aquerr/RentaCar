package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.model.RentaCarUserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RentaCarUserCredentials, Long>
{
    RentaCarUserCredentials findByUsername(String username);

    RentaCarUserCredentials findByEmail(String email);
}
