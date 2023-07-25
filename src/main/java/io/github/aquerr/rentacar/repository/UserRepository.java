package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.user.model.RentaCarUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RentaCarUser, Long>
{
    RentaCarUser findByUsername(String username);
}
