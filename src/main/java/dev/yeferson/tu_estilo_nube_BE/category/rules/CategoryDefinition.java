package dev.yeferson.tu_estilo_nube_BE.category.rules;

import java.util.List;

public class CategoryDefinition {
    private String base_category;
    private List<String> keywords;

    public String getBase_category() {
        return base_category;
    }

    public void setBase_category(String base_category) {
        this.base_category = base_category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}