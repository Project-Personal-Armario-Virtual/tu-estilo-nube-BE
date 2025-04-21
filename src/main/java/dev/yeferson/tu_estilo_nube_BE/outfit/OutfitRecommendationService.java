package dev.yeferson.tu_estilo_nube_BE.outfit;

import dev.yeferson.tu_estilo_nube_BE.image.Image;
import dev.yeferson.tu_estilo_nube_BE.image.ImageRepository;
import dev.yeferson.tu_estilo_nube_BE.outfit.dto.ClothingItemDTO;
import dev.yeferson.tu_estilo_nube_BE.outfit.dto.OutfitRecommendationDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutfitRecommendationService {

    private final ImageRepository imageRepository;

    public OutfitRecommendationService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<OutfitRecommendationDTO> generateOutfits(Long userId) {
        List<ClothingItemDTO> tops = findByCategory(userId, "Tops");
        List<ClothingItemDTO> bottoms = findByCategory(userId, "Bottoms");
        List<ClothingItemDTO> shoes = findByCategory(userId, "Shoes");
        List<ClothingItemDTO> accessories = findByCategory(userId, "Accessories");

        List<OutfitRecommendationDTO> recommendations = new ArrayList<>();

        for (ClothingItemDTO top : tops) {
            for (ClothingItemDTO bottom : bottoms) {
                for (ClothingItemDTO shoe : shoes) {
                    ClothingItemDTO accessory = accessories.isEmpty()
                            ? null
                            : accessories.get(new Random().nextInt(accessories.size()));

                    double score = calculateScore(top, bottom, shoe, accessory);

                    recommendations.add(new OutfitRecommendationDTO(
                            top,
                            bottom,
                            shoe,
                            accessory,
                            "Casual", 
                            "Spring/Summer",
                            score
                    ));
                }
            }
        }

        
        recommendations.sort(Comparator.comparingDouble(OutfitRecommendationDTO::getScore).reversed());

   
        return recommendations.size() > 10 ? recommendations.subList(0, 10) : recommendations;
    }

    private List<ClothingItemDTO> findByCategory(Long userId, String categoryName) {
        return imageRepository.findByUserIdAndCategory_NameIgnoreCase(userId, categoryName).stream()
                .map(image -> new ClothingItemDTO(
                        image.getId(),
                        image.getCategory().getName(),
                        image.getDominantColor(),
                        "/api/images/" + image.getId() + "/preview"
                ))
                .collect(Collectors.toList());
    }

    private double calculateScore(ClothingItemDTO top, ClothingItemDTO bottom, ClothingItemDTO shoes, ClothingItemDTO accessory) {
       
        return Math.random(); 
    }
}
