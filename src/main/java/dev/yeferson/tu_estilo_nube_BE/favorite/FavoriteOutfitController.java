package dev.yeferson.tu_estilo_nube_BE.favorite;

import dev.yeferson.tu_estilo_nube_BE.favorite.dto.FavoriteOutfitResponseDTO;
import dev.yeferson.tu_estilo_nube_BE.outfit.Outfit;
import dev.yeferson.tu_estilo_nube_BE.outfit.OutfitService;
import dev.yeferson.tu_estilo_nube_BE.profile.Profile;
import dev.yeferson.tu_estilo_nube_BE.profile.ProfileService;
import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteOutfitController {

    private final FavoriteOutfitService favoriteOutfitService;
    private final ProfileService profileService;
    private final OutfitService outfitService;
    private final JwtUtil jwtUtil;

    public FavoriteOutfitController(
            FavoriteOutfitService favoriteOutfitService,
            ProfileService profileService,
            OutfitService outfitService,
            JwtUtil jwtUtil
    ) {
        this.favoriteOutfitService = favoriteOutfitService;
        this.profileService = profileService;
        this.outfitService = outfitService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{outfitId}")
    public ResponseEntity<?> addToFavorites(
            HttpServletRequest request,
            @PathVariable Long outfitId
    ) {
        Long userId = jwtUtil.getUserIdFromRequest(request);
        Profile profile = profileService.getByUserId(userId);
        Outfit outfit = outfitService.getById(outfitId);

        favoriteOutfitService.save(profile, outfit);
        return ResponseEntity.ok("Outfit added to favorites.");
    }

    @GetMapping
    public ResponseEntity<List<FavoriteOutfitResponseDTO>> getFavorites(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromRequest(request);
        Profile profile = profileService.getByUserId(userId);

        List<FavoriteOutfit> favorites = favoriteOutfitService.getFavorites(profile);

        List<FavoriteOutfitResponseDTO> response = favorites.stream()
                .map(FavoriteOutfitResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{outfitId}")
    public ResponseEntity<?> removeFromFavorites(
            HttpServletRequest request,
            @PathVariable Long outfitId
    ) {
        Long userId = jwtUtil.getUserIdFromRequest(request);
        Profile profile = profileService.getByUserId(userId);
        Outfit outfit = outfitService.getById(outfitId);

        favoriteOutfitService.removeFavorite(profile, outfit);
        return ResponseEntity.ok("Outfit removed from favorites.");
    }
}