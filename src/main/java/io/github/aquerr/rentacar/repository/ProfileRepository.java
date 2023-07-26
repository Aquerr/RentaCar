package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.profile.model.RentaCarUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<RentaCarUserProfile, Long> {
    RentaCarUserProfile findByCredentialsId(Long credentialsId);
}
