package dev.yeferson.tu_estilo_nube_BE.image;

import java.util.List;

public class ImageDTO {
    private Long id;
    private String fileName;
    private Long userId;
    private List<String> labels;
    private String categoryName; 
    private String dominantColor; 

    public ImageDTO(Long id, String fileName, Long userId, List<String> labels, String categoryName, String dominantColor) {
        this.id = id;
        this.fileName = fileName;
        this.userId = userId;
        this.labels = labels;
        this.categoryName = categoryName;
        this.dominantColor = dominantColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor;
    }
}
