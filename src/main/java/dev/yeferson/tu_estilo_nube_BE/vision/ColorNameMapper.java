package dev.yeferson.tu_estilo_nube_BE.vision;

import com.google.type.Color;

public class ColorNameMapper {

    private ColorNameMapper() {}

    public static String mapRgbToColorName(Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float brightness = (r + g + b) / 3f;

        if (r > 0.92 && g > 0.92 && b > 0.92) return "White";
        if (r < 0.1 && g < 0.1 && b < 0.1) return "Black";
        if (Math.abs(r - g) < 0.05 && Math.abs(r - b) < 0.05) return "Gray";

        // Mejoras en detección de rojo vs rosado
        if (r > 0.8 && g < 0.4 && b < 0.4) return "Red";
        if (r > 0.8 && g > 0.5 && b > 0.5) return "Pink";

        if (r > 0.75 && g > 0.5 && b < 0.4) return "Orange";
        if (r > 0.85 && g > 0.85 && b < 0.5) return "Yellow";
        if (r > 0.6 && g < 0.4 && b > 0.6) return "Purple";
        if (b > 0.7 && r < 0.5 && g > 0.5) return "Teal";
        if (b > 0.6 && r > 0.5 && g > 0.5) return "Light Purple";
        if (r > 0.7 && g > 0.6 && b > 0.5) return "Beige";
        if (r > 0.5 && g > 0.3 && b < 0.2) return "Brown";

        if (g > 0.7 && b < 0.4 && r < 0.4) return brightness < 0.4 ? "Dark Green" : "Green";
        if (b > 0.7 && r < 0.4 && g < 0.4) return brightness < 0.4 ? "Navy Blue" : "Blue";

        return "Unknown";
    }
}
