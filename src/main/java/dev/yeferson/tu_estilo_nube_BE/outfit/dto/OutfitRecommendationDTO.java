package dev.yeferson.tu_estilo_nube_BE.outfit.dto;

public class OutfitRecommendationDTO {
    private ClothingItemDTO top;
    private ClothingItemDTO bottom;
    private ClothingItemDTO shoes;
    private ClothingItemDTO accessory;
    private String occasion;
    private String season;
    private double score;

    public OutfitRecommendationDTO(ClothingItemDTO top, ClothingItemDTO bottom, ClothingItemDTO shoes, ClothingItemDTO accessory, String occasion, String season, double score) {
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.accessory = accessory;
        this.occasion = occasion;
        this.season = season;
        this.score = score;
    }

    public ClothingItemDTO getTop() { return top; }
    public ClothingItemDTO getBottom() { return bottom; }
    public ClothingItemDTO getShoes() { return shoes; }
    public ClothingItemDTO getAccessory() { return accessory; }
    public String getOccasion() { return occasion; }
    public String getSeason() { return season; }
    public double getScore() { return score; }
}
