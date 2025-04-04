package dev.yeferson.tu_estilo_nube_BE.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.yeferson.tu_estilo_nube_BE.user.User;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUser(User user);

    @Query("SELECT new dev.yeferson.tu_estilo_nube_BE.image.ImageDTO(i.id, i.fileName, i.user.id) FROM Image i WHERE i.user = :user")
    List<ImageDTO> findImageDTOsByUser(@Param("user") User user);
}