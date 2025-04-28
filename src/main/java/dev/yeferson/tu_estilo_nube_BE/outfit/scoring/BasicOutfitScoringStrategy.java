package dev.yeferson.tu_estilo_nube_BE.outfit.scoring;

import dev.yeferson.tu_estilo_nube_BE.outfit.dto.ClothingItemDTO;
import org.springframework.stereotype.Component;

@Component
public class BasicOutfitScoringStrategy implements OutfitScoringStrategy {

    @Override
    public double calculateScore(
        ClothingItemDTO top,
        ClothingItemDTO bottom,
        ClothingItemDTO shoes,
        ClothingItemDTO accessory,
        String occasion,
        String season
    ) {
        double score = 0.0;

        if (top.getDominantColor().equalsIgnoreCase(bottom.getDominantColor())) score += 0.2;
        if (shoes.getDominantColor().equalsIgnoreCase(bottom.getDominantColor())) score += 0.1;
        if (accessory != null && accessory.getDominantColor().equalsIgnoreCase(top.getDominantColor())) score += 0.1;

        if (bottom.getCategory().toLowerCase().contains("casual") && shoes.getCategory().equalsIgnoreCase("Shoes"))
            score += 0.15;
        if (top.getCategory().toLowerCase().contains("shirt") && bottom.getCategory().toLowerCase().contains("pants"))
            score += 0.15;

        if (occasion.equalsIgnoreCase("casual")) score += 0.15;
        if (season.equalsIgnoreCase("spring")) score += 0.15;

        return score;
    }
}