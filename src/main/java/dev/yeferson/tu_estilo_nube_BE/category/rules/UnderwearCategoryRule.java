package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.List;
import java.util.stream.Collectors;

public class UnderwearCategoryRule implements CategoryRule {
    @Override
    public String apply(ProcessedImageData data) {
        List<String> lowerLabels = data.getLabels().stream()
                                       .map(String::toLowerCase)
                                       .collect(Collectors.toList());
       
        if (lowerLabels.contains("bra") || lowerLabels.contains("lingerie") ||
            lowerLabels.contains("undergarment") || lowerLabels.contains("sports bra")) {
            return "Underwear / Sports Bras";
        }
        return null;
    }
}