package dev.yeferson.tu_estilo_nube_BE.category.rules;

import dev.yeferson.tu_estilo_nube_BE.vision.VisionService.ProcessedImageData;
import java.util.List;
import java.util.stream.Collectors;

public class SportsUpperWearRule implements CategoryRule {

    @Override
    public String apply(ProcessedImageData data) {
        List<String> lowerLabels = data.getLabels().stream()
                                       .map(String::toLowerCase)
                                       .collect(Collectors.toList());
        if ((lowerLabels.contains("shirt") || lowerLabels.contains("top") || lowerLabels.contains("blouse"))
            && (lowerLabels.contains("active") || lowerLabels.contains("sports") || lowerLabels.contains("yoga") || lowerLabels.contains("training"))) {
            return "Sports Upper Wear";
        }
        return null;
    }
}