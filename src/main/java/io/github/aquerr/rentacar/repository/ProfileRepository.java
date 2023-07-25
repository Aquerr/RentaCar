package io.github.aquerr.rentacar.repository;

import io.github.aquerr.rentacar.domain.profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
}
