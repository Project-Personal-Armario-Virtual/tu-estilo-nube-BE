package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.ArrayList;
import java.util.List;

public class CategoryMappingService {
    
    private final List<CategoryRule> rules;
    
    public CategoryMappingService() {
        rules = new ArrayList<>();
      
        rules.add(new ShirtCategoryRule());
        rules.add(new PantsCategoryRule());
        rules.add(new ShoeCategoryRule());
        
      
        rules.add(new SportsUpperWearRule());
        rules.add(new PantsStyleRule());
        rules.add(new ColorBasedRule());
       
    }
    

    public String suggestCategory(ProcessedImageData data) {
        for (CategoryRule rule : rules) {
            String category = rule.apply(data);
            if (category != null) {
                return category;
            }
        }
        return "Uncategorized";
    }
}