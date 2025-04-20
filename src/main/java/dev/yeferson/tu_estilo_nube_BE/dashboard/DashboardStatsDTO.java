package dev.yeferson.tu_estilo_nube_BE.dashboard;

public class DashboardStatsDTO {
    private int totalItems;
    private int totalCategories;
    private String mostCommonColor;

    public DashboardStatsDTO(int totalItems, int totalCategories, String mostCommonColor) {
        this.totalItems = totalItems;
        this.totalCategories = totalCategories;
        this.mostCommonColor = mostCommonColor;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(int totalCategories) {
        this.totalCategories = totalCategories;
    }

    public String getMostCommonColor() {
        return mostCommonColor;
    }

    public void setMostCommonColor(String mostCommonColor) {
        this.mostCommonColor = mostCommonColor;
    }

}
