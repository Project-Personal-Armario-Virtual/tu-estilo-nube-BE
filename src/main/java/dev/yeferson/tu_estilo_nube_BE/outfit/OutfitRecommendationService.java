package dev.yeferson.tu_estilo_nube_BE.outfit;



import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OutfitRecommendationService {

    public List<OutfitRecommendationDTO> generateOutfits(Long userId) {
        // TODO: replace with real DB query
        List<ClothingItemDTO> tops = getItemsByCategory(userId, "Tops");
        List<ClothingItemDTO> bottoms = getItemsByCategory(userId, "Bottoms");
        List<ClothingItemDTO> shoes = getItemsByCategory(userId, "Shoes");
        List<ClothingItemDTO> accessories = getItemsByCategory(userId, "Accessories");

        List<OutfitRecommendationDTO> recommendations = new ArrayList<>();

        for (ClothingItemDTO top : tops) {
            for (ClothingItemDTO bottom : bottoms) {
                for (ClothingItemDTO shoe : shoes) {
                    ClothingItemDTO accessory = accessories.isEmpty() ? null : accessories.get(new Random().nextInt(accessories.size()));

                    double score = calculateScore(top, bottom, shoe, accessory);
                    OutfitRecommendationDTO outfit = new OutfitRecommendationDTO(top, bottom, shoe, accessory, "Casual", "Spring/Summer", score);
                    recommendations.add(outfit);
                }
            }
        }

        recommendations.sort(Comparator.comparingDouble(OutfitRecommendationDTO::getScore).reversed());
        return recommendations.size() > 10 ? recommendations.subList(0, 10) : recommendations;
    }

    private List<ClothingItemDTO> getItemsByCategory(Long userId, String category) {
        // Mocked data for now
        return new ArrayList<>();
    }

    private double calculateScore(ClothingItemDTO top, ClothingItemDTO bottom, ClothingItemDTO shoes, ClothingItemDTO accessory) {
        // TODO: implement real scoring logic
        return Math.random();
    }

}
