package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PantsCategoryRule implements CategoryRule {

    private final List<String> pantsKeywords;

    public PantsCategoryRule() {
        Map<String, CategoryDefinition> dictionary = CategoryDictionaryLoader.loadDictionary();
        CategoryDefinition lowerWearDef = dictionary.get("lower_wear");
        this.pantsKeywords = lowerWearDef.getKeywords().stream()
                            .map(String::toLowerCase)
                            .collect(Collectors.toList());
    }

    @Override
    public String apply(ProcessedImageData data) {
        List<String> lowerLabels = data.getLabels().stream()
                                       .map(String::toLowerCase)
                                       .collect(Collectors.toList());
        for (String label : lowerLabels) {
            for (String keyword : pantsKeywords) {
                if (label.contains(keyword)) {
                    if ("black".equalsIgnoreCase(data.getDominantColor())) {
                        return "Black Pants";
                    }
                    return "Pants";
                }
            }
        }
        return null;
    }
}