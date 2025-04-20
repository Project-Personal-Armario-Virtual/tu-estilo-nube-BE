package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.List;
import java.util.stream.Collectors;

public class PantsStyleRule implements CategoryRule {

    @Override
    public String apply(ProcessedImageData data) {
        List<String> lowerLabels = data.getLabels().stream()
                                       .map(String::toLowerCase)
                                       .collect(Collectors.toList());
        if (lowerLabels.contains("jeans") || lowerLabels.contains("denim")) {
            return "Casual Pants";
        }
        if (lowerLabels.contains("dress pants") || lowerLabels.contains("trousers")) {
            return "Formal Pants";
        }
        return null;
    }
}