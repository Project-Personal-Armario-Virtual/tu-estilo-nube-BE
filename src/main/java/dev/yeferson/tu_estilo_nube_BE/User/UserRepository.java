package dev.yeferson.tu_estilo_nube_BE.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   
    Optional<User> findByUsername(String username);
}
