package dev.yeferson.tu_estilo_nube_BE.favorite.dto;

import dev.yeferson.tu_estilo_nube_BE.favorite.FavoriteOutfit;
import dev.yeferson.tu_estilo_nube_BE.outfit.dto.ClothingItemDTO;
import dev.yeferson.tu_estilo_nube_BE.image.Image;

public class FavoriteOutfitResponseDTO {

    private Long id;
    private ClothingItemDTO top;
    private ClothingItemDTO bottom;
    private ClothingItemDTO shoes;
    private ClothingItemDTO accessory;
    private String occasion;
    private String season;
    private double score;

    public FavoriteOutfitResponseDTO(FavoriteOutfit favorite) {
        this.id = favorite.getId();
        this.top = mapToDTO(favorite.getOutfit().getTop());
        this.bottom = mapToDTO(favorite.getOutfit().getBottom());
        this.shoes = mapToDTO(favorite.getOutfit().getShoes());
        this.accessory = favorite.getOutfit().getAccessory() != null
                ? mapToDTO(favorite.getOutfit().getAccessory())
                : null;
        this.occasion = favorite.getOutfit().getOccasion();
        this.season = favorite.getOutfit().getSeason();
        this.score = favorite.getOutfit().getScore();
    }

    private ClothingItemDTO mapToDTO(Image image) {
        return new ClothingItemDTO(
                image.getId(),
                image.getCategory().getName(),
                image.getDominantColor(),
                "/api/images/" + image.getId() + "/preview"
        );
    }

    
    public Long getId() {
        return id;
    }

    public ClothingItemDTO getTop() {
        return top;
    }

    public ClothingItemDTO getBottom() {
        return bottom;
    }

    public ClothingItemDTO getShoes() {
        return shoes;
    }

    public ClothingItemDTO getAccessory() {
        return accessory;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getSeason() {
        return season;
    }

    public double getScore() {
        return score;
    }
}
