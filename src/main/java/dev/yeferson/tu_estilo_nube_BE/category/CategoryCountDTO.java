package dev.yeferson.tu_estilo_nube_BE.category;

public class CategoryCountDTO {

    private String categoryName;
    private long itemCount;

    public CategoryCountDTO(String categoryName, long itemCount) {
        this.categoryName = categoryName;
        this.itemCount = itemCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }

}
