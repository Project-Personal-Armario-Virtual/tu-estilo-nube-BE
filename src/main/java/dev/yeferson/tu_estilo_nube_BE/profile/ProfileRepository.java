package dev.yeferson.tu_estilo_nube_BE.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.yeferson.tu_estilo_nube_BE.user.User;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByUserId(Long userId);
}