package dev.yeferson.tu_estilo_nube_BE.outfit;

import dev.yeferson.tu_estilo_nube_BE.outfit.OutfitRecommendationDTO;
import dev.yeferson.tu_estilo_nube_BE.outfit.OutfitRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/outfits")
public class OutfitController {

    @Autowired
    private OutfitRecommendationService outfitRecommendationService;

    @GetMapping("/recommendations")
    public List<OutfitRecommendationDTO> getRecommendations(@RequestParam Long userId) {
        return outfitRecommendationService.generateOutfits(userId);
    }
} 
