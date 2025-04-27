package dev.yeferson.tu_estilo_nube_BE.outfit;

import dev.yeferson.tu_estilo_nube_BE.outfit.dto.ClothingItemDTO;
import dev.yeferson.tu_estilo_nube_BE.outfit.dto.OutfitRecommendationDTO;
import dev.yeferson.tu_estilo_nube_BE.outfit.dto.OutfitRequestDTO;
import dev.yeferson.tu_estilo_nube_BE.outfit.dto.SaveOutfitRequestDTO;
import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import dev.yeferson.tu_estilo_nube_BE.image.ImageRepository;
import dev.yeferson.tu_estilo_nube_BE.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/outfits")
public class OutfitController {

    private final OutfitRecommendationService recommendationService;
    private final OutfitService outfitService;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public OutfitController(
            OutfitRecommendationService recommendationService,
            OutfitService outfitService,
            ImageRepository imageRepository,
            UserRepository userRepository,
            JwtUtil jwtUtil) {
        this.recommendationService = recommendationService;
        this.outfitService = outfitService;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/recommendations")
    public ResponseEntity<List<OutfitRecommendationDTO>> generateCustomRecommendations(
            HttpServletRequest request,
            @RequestBody OutfitRequestDTO outfitRequest) {

        Long userId = jwtUtil.getUserIdFromRequest(request);
        List<OutfitRecommendationDTO> outfits = recommendationService.generateOutfits(userId, outfitRequest);
        return ResponseEntity.ok(outfits);
    }

    @PostMapping
    public ResponseEntity<Long> saveOutfit(HttpServletRequest request, @RequestBody SaveOutfitRequestDTO dto) {
        Long userId = jwtUtil.getUserIdFromRequest(request);

        Outfit outfit = new Outfit();
        outfit.setUser(userRepository.findById(userId).orElseThrow());
        outfit.setTop(dto.getTopId() != null ? imageRepository.findById(dto.getTopId()).orElse(null) : null);
        outfit.setBottom(dto.getBottomId() != null ? imageRepository.findById(dto.getBottomId()).orElse(null) : null);
        outfit.setShoes(dto.getShoesId() != null ? imageRepository.findById(dto.getShoesId()).orElse(null) : null);
        outfit.setAccessory(
                dto.getAccessoryId() != null ? imageRepository.findById(dto.getAccessoryId()).orElse(null) : null);
        outfit.setOccasion(dto.getOccasion());
        outfit.setSeason(dto.getSeason());
        outfit.setScore(dto.getScore());

        Outfit savedOutfit = outfitService.saveOutfit(outfit);

        return ResponseEntity.ok(savedOutfit.getId());
    }

    @GetMapping("/mine")
    public ResponseEntity<List<OutfitRecommendationDTO>> getMyOutfits(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromRequest(request);
        List<Outfit> outfits = outfitService.getOutfitsByUserId(userId);

        List<OutfitRecommendationDTO> outfitDTOs = outfits.stream().map(outfit -> {
            return new OutfitRecommendationDTO(
                    outfit.getId(), 
                    outfit.getTop() != null ? new ClothingItemDTO(outfit.getTop()) : null,
                    outfit.getBottom() != null ? new ClothingItemDTO(outfit.getBottom()) : null,
                    outfit.getShoes() != null ? new ClothingItemDTO(outfit.getShoes()) : null,
                    outfit.getAccessory() != null ? new ClothingItemDTO(outfit.getAccessory()) : null,
                    outfit.getOccasion(),
                    outfit.getSeason(),
                    outfit.getScore());
        }).toList();

        return ResponseEntity.ok(outfitDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutfit(HttpServletRequest request, @PathVariable Long id) {
        Long userId = jwtUtil.getUserIdFromRequest(request);
        Outfit outfit = outfitService.getById(id);

        if (!outfit.getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        outfitService.deleteOutfit(id);
        return ResponseEntity.noContent().build();
    }
}
