package dev.yeferson.tu_estilo_nube_BE.outfit;

public class ClothingItemDTO {

    private Long id;
    private String category;
    private String color;
    private String imageUrl;


    public ClothingItemDTO() {}

    public ClothingItemDTO(Long id, String category, String color, String imageUrl) {
        this.id = id;
        this.category = category;
        this.color = color;
        this.imageUrl = imageUrl;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
