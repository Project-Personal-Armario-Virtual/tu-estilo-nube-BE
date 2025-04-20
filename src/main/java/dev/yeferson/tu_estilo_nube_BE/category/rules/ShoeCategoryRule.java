package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoeCategoryRule implements CategoryRule {

    private final List<String> shoeKeywords;

    public ShoeCategoryRule() {
        Map<String, CategoryDefinition> dictionary = CategoryDictionaryLoader.loadDictionary();
        CategoryDefinition footwearDef = dictionary.get("footwear");
        this.shoeKeywords = footwearDef.getKeywords().stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    }

    @Override
    public String apply(ProcessedImageData data) {
        List<String> lowerLabels = data.getLabels().stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
        for (String label : lowerLabels) {
            for (String keyword : shoeKeywords) {
                if (label.contains(keyword)) {
                    return "Shoes";
                }
            }
        }
        return null;
    }
}