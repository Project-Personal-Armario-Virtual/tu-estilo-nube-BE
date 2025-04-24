package dev.yeferson.tu_estilo_nube_BE.image;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.yeferson.tu_estilo_nube_BE.category.CategoryCountDTO;
import dev.yeferson.tu_estilo_nube_BE.user.User;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByUser(User user);

    List<Image> findTop5ByUserOrderByCreatedAtDesc(User user);

    List<Image> findByUserIdAndCategory_NameIgnoreCase(Long userId, String categoryName);
    List<Image> findByUserId(Long userId);
    Optional<Image> findByIdAndUser(Long id, User user);

  
    @Query("SELECT new dev.yeferson.tu_estilo_nube_BE.category.CategoryCountDTO(" +
       "COALESCE(i.category.name, 'Uncategorized'), COUNT(i)) " +
       "FROM Image i " +
       "WHERE i.user = :user " +
       "GROUP BY i.category.name")
List<CategoryCountDTO> countImagesByCategory(@Param("user") User user);

}
