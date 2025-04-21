package dev.yeferson.tu_estilo_nube_BE.outfit.scoring;



import dev.yeferson.tu_estilo_nube_BE.outfit.dto.ClothingItemDTO;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class OutfitScoringService {

   
    public double calculateScore(ClothingItemDTO top, ClothingItemDTO bottom, ClothingItemDTO shoes, ClothingItemDTO accessory, String season, String occasion) {
        double score = 0.5;

      
        if (top != null && bottom != null && top.getDominantColor().equalsIgnoreCase(bottom.getDominantColor())) {
            score += 0.1;
        }

        if (bottom != null && shoes != null && bottom.getDominantColor().equalsIgnoreCase(shoes.getDominantColor())) {
            score += 0.1;
        }

      
        score += getSeasonBonus(top, season);
        score += getSeasonBonus(bottom, season);
        score += getSeasonBonus(shoes, season);

      
        score += getOccasionBonus(shoes, occasion);
        score += getOccasionBonus(top, occasion);

        return Math.min(score, 1.0); 
    }

   
    private double getSeasonBonus(ClothingItemDTO item, String season) {
        if (item == null || season == null) return 0;

        String cat = item.getCategory().toLowerCase(Locale.ROOT);
        season = season.toLowerCase(Locale.ROOT);

        return switch (season) {
            case "winter" -> (cat.contains("coat") || cat.contains("sweater") || cat.contains("jacket")) ? 0.15 : 0;
            case "summer" -> (cat.contains("t-shirt") || cat.contains("shorts") || cat.contains("top")) ? 0.1 : 0;
            case "spring", "fall" -> (cat.contains("shirt") || cat.contains("pants")) ? 0.05 : 0;
            default -> 0;
        };
    }

 
    private double getOccasionBonus(ClothingItemDTO item, String occasion) {
        if (item == null || occasion == null) return 0;

        String cat = item.getCategory().toLowerCase(Locale.ROOT);
        occasion = occasion.toLowerCase(Locale.ROOT);

        return switch (occasion) {
            case "formal" -> (cat.contains("blazer") || cat.contains("shirt") || cat.contains("dress shoes")) ? 0.1 : 0;
            case "casual" -> (cat.contains("jeans") || cat.contains("sneaker") || cat.contains("t-shirt")) ? 0.1 : 0;
            case "work" -> (cat.contains("shirt") || cat.contains("polo") || cat.contains("trousers")) ? 0.1 : 0;
            case "sport" -> (cat.contains("sneaker") || cat.contains("tracksuit") || cat.contains("sports")) ? 0.1 : 0;
            case "party" -> (cat.contains("shiny") || cat.contains("bright") || cat.contains("stylish")) ? 0.05 : 0;
            default -> 0;
        };
    }
}
