package dev.yeferson.tu_estilo_nube_BE.outfit.dto;

import dev.yeferson.tu_estilo_nube_BE.image.Image;

public class ClothingItemDTO {
    private Long id;
    private String category;
    private String dominantColor;
    private String imageUrl;

 
    public ClothingItemDTO(Long id, String category, String dominantColor, String imageUrl) {
        this.id = id;
        this.category = category;
        this.dominantColor = dominantColor;
        this.imageUrl = imageUrl;
    }

    
    public ClothingItemDTO(Image image) {
        this.id = image.getId();
        this.category = image.getCategory().getName();
        this.dominantColor = image.getDominantColor();
        this.imageUrl = "/api/images/" + image.getId() + "/preview";
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}