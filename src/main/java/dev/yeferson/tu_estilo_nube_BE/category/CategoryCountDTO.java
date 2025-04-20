package dev.yeferson.tu_estilo_nube_BE.category;

public class CategoryCountDTO {

    private String categoryName;
    private Long itemCount;

    public CategoryCountDTO(String categoryName, Long itemCount) {
        this.categoryName = categoryName;
        this.itemCount = itemCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getItemCount() {
        return itemCount;
    }

}
