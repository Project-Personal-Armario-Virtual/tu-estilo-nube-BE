package dev.yeferson.tu_estilo_nube_BE.image;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import dev.yeferson.tu_estilo_nube_BE.user.User;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
    List<Image> findByUser(User user);
}
