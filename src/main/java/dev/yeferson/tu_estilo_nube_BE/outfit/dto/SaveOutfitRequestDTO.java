package dev.yeferson.tu_estilo_nube_BE.outfit.dto;

public class SaveOutfitRequestDTO {

    private Long topId;
    private Long bottomId;
    private Long shoesId;
    private Long accessoryId;
    private String occasion;
    private String season;
    private double score;


    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Long getBottomId() {
        return bottomId;
    }

    public void setBottomId(Long bottomId) {
        this.bottomId = bottomId;
    }

    public Long getShoesId() {
        return shoesId;
    }

    public void setShoesId(Long shoesId) {
        this.shoesId = shoesId;
    }

    public Long getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
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
