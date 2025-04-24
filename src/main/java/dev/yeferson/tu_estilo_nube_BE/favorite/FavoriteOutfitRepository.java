package dev.yeferson.tu_estilo_nube_BE.favorite;

import dev.yeferson.tu_estilo_nube_BE.outfit.Outfit;
import dev.yeferson.tu_estilo_nube_BE.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteOutfitRepository extends JpaRepository<FavoriteOutfit, Long> {
    List<FavoriteOutfit> findByProfile(Profile profile);

    boolean existsByProfileAndOutfit(Profile profile, Outfit outfit);

    Optional<FavoriteOutfit> findByProfileAndOutfit(Profile profile, Outfit outfit);
}
