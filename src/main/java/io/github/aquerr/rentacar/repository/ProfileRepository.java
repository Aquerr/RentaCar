package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    UserProfileEntity findByUserId(Long userId);
}
