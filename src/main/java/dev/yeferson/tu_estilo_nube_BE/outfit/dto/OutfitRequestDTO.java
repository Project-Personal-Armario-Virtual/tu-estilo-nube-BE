package dev.yeferson.tu_estilo_nube_BE.outfit.dto;

public class OutfitRequestDTO {
    private String occasion;
    private String season;
    private boolean includeAccessories;

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

    public boolean isIncludeAccessories() {
        return includeAccessories;
    }

    public void setIncludeAccessories(boolean includeAccessories) {
        this.includeAccessories = includeAccessories;
    }
}