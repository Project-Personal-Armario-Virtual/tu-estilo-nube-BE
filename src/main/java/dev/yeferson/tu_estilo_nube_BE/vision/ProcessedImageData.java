package dev.yeferson.tu_estilo_nube_BE.vision;

import java.util.List;

public class ProcessedImageData {

    private final List<String> labels;
    private final String dominantColor;
    private final Float dominantColorFraction; 

    public ProcessedImageData(List<String> labels, String dominantColor, Float dominantColorFraction) {
        this.labels = labels;
        this.dominantColor = dominantColor;
        this.dominantColorFraction = dominantColorFraction;
    }

    public List<String> getLabels() {
        return labels;
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public Float getDominantColorFraction() {
        return dominantColorFraction;
    }

}
