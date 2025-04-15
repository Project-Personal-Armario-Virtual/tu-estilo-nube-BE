package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShirtCategoryRule implements CategoryRule {

    private final List<String> shirtKeywords;

    public ShirtCategoryRule() {
        Map<String, CategoryDefinition> dictionary = CategoryDictionaryLoader.loadDictionary();
        CategoryDefinition upperWearDef = dictionary.get("upper_wear");
        this.shirtKeywords = upperWearDef.getKeywords().stream()
                              .map(String::toLowerCase)
                              .collect(Collectors.toList());
    }

    @Override
    public String apply(ProcessedImageData data) {
        List<String> lowerLabels = data.getLabels().stream()
                                       .map(String::toLowerCase)
                                       .collect(Collectors.toList());
        for (String label : lowerLabels) {
            for (String keyword : shirtKeywords) {
                if (label.contains(keyword)) {
  
                    if (lowerLabels.contains("yellow")) {
                        return "Yellow Shirts";
                    }
                    return "Shirts";
                }
            }
        }
        return null;
    }
}