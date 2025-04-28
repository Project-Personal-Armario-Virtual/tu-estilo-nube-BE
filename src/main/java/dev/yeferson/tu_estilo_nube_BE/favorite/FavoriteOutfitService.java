package dev.yeferson.tu_estilo_nube_BE.favorite;

import dev.yeferson.tu_estilo_nube_BE.outfit.Outfit;

import dev.yeferson.tu_estilo_nube_BE.profile.Profile;
import dev.yeferson.tu_estilo_nube_BE.profile.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteOutfitService {

    private final FavoriteOutfitRepository favoriteOutfitRepository;
    private final ProfileService profileService;

    public FavoriteOutfitService(
            FavoriteOutfitRepository favoriteOutfitRepository,
            ProfileService profileService) {
        this.favoriteOutfitRepository = favoriteOutfitRepository;
        this.profileService = profileService;
    }

    public FavoriteOutfit save(Profile profile, Outfit outfit) {
        if (!favoriteOutfitRepository.existsByProfileAndOutfit(profile, outfit)) {
            return favoriteOutfitRepository.save(new FavoriteOutfit(profile, outfit));
        }
        return null;
    }

    public List<FavoriteOutfit> getFavorites(Profile profile) {
        return favoriteOutfitRepository.findByProfile(profile);
    }

    public void removeFavorite(Profile profile, Outfit outfit) {
        favoriteOutfitRepository.findByProfileAndOutfit(profile, outfit)
                .ifPresent(favoriteOutfitRepository::delete);
    }

    public List<FavoriteOutfit> getFavoritesByUserId(Long userId) {
        Profile profile = profileService.getByUserId(userId);
        return favoriteOutfitRepository.findByProfile(profile);
    }
}
