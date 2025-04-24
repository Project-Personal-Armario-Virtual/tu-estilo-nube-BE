package dev.yeferson.tu_estilo_nube_BE.category.rules;

import java.util.HashMap;
import java.util.Map;

public class CategoryBaseMapper {

    private static final Map<String, String> CATEGORY_TO_BASE = new HashMap<>();

    static {
        CATEGORY_TO_BASE.put("Shirts", "Tops");
        CATEGORY_TO_BASE.put("Upper Wear - Black", "Tops");
        CATEGORY_TO_BASE.put("Blouse", "Tops");

        CATEGORY_TO_BASE.put("Pants", "Bottoms");
        CATEGORY_TO_BASE.put("Black Pants", "Bottoms");
        CATEGORY_TO_BASE.put("Casual Pants", "Bottoms");
        CATEGORY_TO_BASE.put("Formal Pants", "Bottoms");

        CATEGORY_TO_BASE.put("Shoes", "Shoes");
        CATEGORY_TO_BASE.put("Sneakers", "Shoes");

        CATEGORY_TO_BASE.put("Underwear", "Accessories");
        CATEGORY_TO_BASE.put("Scarf", "Accessories");
    }

    public static String getBaseCategory(String categoryName) {
        return CATEGORY_TO_BASE.getOrDefault(categoryName, "Uncategorized");
    }
}