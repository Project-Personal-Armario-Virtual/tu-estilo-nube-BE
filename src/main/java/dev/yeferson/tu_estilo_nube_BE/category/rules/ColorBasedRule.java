package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.List;
import java.util.stream.Collectors;

public class ColorBasedRule implements CategoryRule {

    @Override
    public String apply(ProcessedImageData data) {
        String dominantColor = data.getDominantColor();
        if (dominantColor != null && dominantColor.equalsIgnoreCase("black")) {
            List<String> lowerLabels = data.getLabels().stream()
                                           .map(String::toLowerCase)
                                           .collect(Collectors.toList());
        
            if (lowerLabels.contains("shirt") || lowerLabels.contains("top") || lowerLabels.contains("blouse")) {
                return "Upper Wear - Black";
            }
          
            if (lowerLabels.contains("pants") || lowerLabels.contains("jeans")) {
                return "Pants - Black";
            }
        }
        return null;
    }
}