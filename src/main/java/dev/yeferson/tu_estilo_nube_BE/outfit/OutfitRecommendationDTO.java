package dev.yeferson.tu_estilo_nube_BE.outfit;

public class OutfitRecommendationDTO {

    private ClothingItemDTO top;
    private ClothingItemDTO bottom;
    private ClothingItemDTO shoes;
    private ClothingItemDTO accessory; 
    private String occasion;
    private String season;
    private double score;

    public OutfitRecommendationDTO() {}

    public OutfitRecommendationDTO(ClothingItemDTO top, ClothingItemDTO bottom, ClothingItemDTO shoes, ClothingItemDTO accessory, String occasion, String season, double score) {
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.accessory = accessory;
        this.occasion = occasion;
        this.season = season;
        this.score = score;
    }


    public ClothingItemDTO getTop() {
        return top;
    }

    public void setTop(ClothingItemDTO top) {
        this.top = top;
    }

    public ClothingItemDTO getBottom() {
        return bottom;
    }

    public void setBottom(ClothingItemDTO bottom) {
        this.bottom = bottom;
    }

    public ClothingItemDTO getShoes() {
        return shoes;
    }

    public void setShoes(ClothingItemDTO shoes) {
        this.shoes = shoes;
    }

    public ClothingItemDTO getAccessory() {
        return accessory;
    }

    public void setAccessory(ClothingItemDTO accessory) {
        this.accessory = accessory;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
