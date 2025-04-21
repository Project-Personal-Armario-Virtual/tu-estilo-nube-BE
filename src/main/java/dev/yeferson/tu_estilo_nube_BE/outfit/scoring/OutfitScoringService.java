package dev.yeferson.tu_estilo_nube_BE.outfit.scoring;

import dev.yeferson.tu_estilo_nube_BE.outfit.dto.ClothingItemDTO;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OutfitScoringService {


    public double calculateScore(ClothingItemDTO top, ClothingItemDTO bottom,
                                 ClothingItemDTO shoes, ClothingItemDTO accessory) {
        double score = 0.0;

        
        if (Objects.equals(top.getDominantColor(), bottom.getDominantColor())) {
            score += 0.3;
        }


        if (Objects.equals(top.getDominantColor(), shoes.getDominantColor())) {
            score += 0.3;
        }

       
        if (Objects.equals(bottom.getDominantColor(), shoes.getDominantColor())) {
            score += 0.2;
        }

        
        if (accessory != null && Objects.equals(accessory.getDominantColor(), top.getDominantColor())) {
            score += 0.2;
        }

        return Math.min(score, 1.0); 
    }
}