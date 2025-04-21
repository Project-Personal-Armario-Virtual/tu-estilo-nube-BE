package dev.yeferson.tu_estilo_nube_BE.outfit;

import dev.yeferson.tu_estilo_nube_BE.auth.dto.OutfitRequestDTO;
import dev.yeferson.tu_estilo_nube_BE.category.rules.CategoryBaseMapper;
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
        List<ClothingItemDTO> tops = findByBaseCategory(userId, "Tops");
        List<ClothingItemDTO> bottoms = findByBaseCategory(userId, "Bottoms");
        List<ClothingItemDTO> shoes = findByBaseCategory(userId, "Shoes");
        List<ClothingItemDTO> accessories = findByBaseCategory(userId, "Accessories");

        List<OutfitRecommendationDTO> recommendations = new ArrayList<>();

        for (ClothingItemDTO top : tops) {
            for (ClothingItemDTO bottom : bottoms) {
                for (ClothingItemDTO shoe : shoes) {
                    ClothingItemDTO accessory = accessories.isEmpty() ? null : accessories.get(new Random().nextInt(accessories.size()));

                    double score = calculateScore(top, bottom, shoe, accessory);
                    recommendations.add(new OutfitRecommendationDTO(
                            top, bottom, shoe, accessory,
                            "Casual", "Spring/Summer", score));
                }
            }
        }

        recommendations.sort(Comparator.comparingDouble(OutfitRecommendationDTO::getScore).reversed());
        return recommendations.size() > 10 ? recommendations.subList(0, 10) : recommendations;
    }

    private List<ClothingItemDTO> findByBaseCategory(Long userId, String baseCategory) {
        List<Image> userImages = imageRepository.findByUserId(userId);

        return userImages.stream()
                .filter(img -> {
                    String realCategory = img.getCategory().getName();
                    String mappedBase = CategoryBaseMapper.getBaseCategory(realCategory);
                    return baseCategory.equalsIgnoreCase(mappedBase);
                })
                .map(img -> new ClothingItemDTO(
                        img.getId(),
                        img.getCategory().getName(),
                        img.getDominantColor(),
                        "/api/images/" + img.getId() + "/preview"))
                .collect(Collectors.toList());
    }

    private double calculateScore(ClothingItemDTO top, ClothingItemDTO bottom, ClothingItemDTO shoes, ClothingItemDTO accessory) {
        // Future: implement logic based on color, season, occasion, etc.
        return Math.random(); // Placeholder
    }

    public List<OutfitRecommendationDTO> generateOutfits(Long userId, OutfitRequestDTO request) {
    List<ClothingItemDTO> tops = findByBaseCategory(userId, "Tops");
    List<ClothingItemDTO> bottoms = findByBaseCategory(userId, "Bottoms");
    List<ClothingItemDTO> shoes = findByBaseCategory(userId, "Shoes");
    List<ClothingItemDTO> accessories = request.isIncludeAccessories()
        ? findByBaseCategory(userId, "Accessories")
        : new ArrayList<>();

    if (tops.isEmpty() || bottoms.isEmpty() || shoes.isEmpty()) {
        return Collections.emptyList();
    }

    List<OutfitRecommendationDTO> recommendations = new ArrayList<>();

    for (ClothingItemDTO top : tops) {
        for (ClothingItemDTO bottom : bottoms) {
            for (ClothingItemDTO shoe : shoes) {
                ClothingItemDTO accessory = accessories.isEmpty() ? null :
                        accessories.get(new Random().nextInt(accessories.size()));

                double score = calculateScore(top, bottom, shoe, accessory);
                recommendations.add(new OutfitRecommendationDTO(
                        top, bottom, shoe, accessory,
                        request.getOccasion(),
                        request.getSeason(),
                        score
                ));
            }
        }
    }

    recommendations.sort(Comparator.comparingDouble(OutfitRecommendationDTO::getScore).reversed());
    return recommendations.size() > 10 ? recommendations.subList(0, 10) : recommendations;
}
}
