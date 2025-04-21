package dev.yeferson.tu_estilo_nube_BE.outfit.scoring;

import dev.yeferson.tu_estilo_nube_BE.outfit.dto.ClothingItemDTO;

public interface OutfitScoringStrategy {

     double calculateScore(
        ClothingItemDTO top,
        ClothingItemDTO bottom,
        ClothingItemDTO shoes,
        ClothingItemDTO accessory,
        String occasion,
        String season
    );

}
