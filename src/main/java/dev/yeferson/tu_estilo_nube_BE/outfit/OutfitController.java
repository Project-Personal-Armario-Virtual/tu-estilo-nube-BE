package dev.yeferson.tu_estilo_nube_BE.outfit;

import dev.yeferson.tu_estilo_nube_BE.auth.dto.OutfitRequestDTO;
import dev.yeferson.tu_estilo_nube_BE.outfit.dto.OutfitRecommendationDTO;
import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/outfits")
public class OutfitController {

    private final OutfitRecommendationService recommendationService;
    private final JwtUtil jwtUtil;

    public OutfitController(OutfitRecommendationService recommendationService, JwtUtil jwtUtil) {
        this.recommendationService = recommendationService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/recommendations")
    public List<OutfitRecommendationDTO> getRecommendations(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromRequest(request);
        return recommendationService.generateOutfits(userId);
    }

    @PostMapping("/recommendations")
public ResponseEntity<List<OutfitRecommendationDTO>> generateCustomRecommendations(
        HttpServletRequest request,
        @RequestBody OutfitRequestDTO outfitRequest) {

    Long userId = jwtUtil.getUserIdFromRequest(request);
    List<OutfitRecommendationDTO> outfits = recommendationService.generateOutfits(userId, outfitRequest);
    return ResponseEntity.ok(outfits);
}
}
