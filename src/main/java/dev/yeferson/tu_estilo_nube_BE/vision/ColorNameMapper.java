package dev.yeferson.tu_estilo_nube_BE.vision;

import com.google.type.Color;

public class ColorNameMapper {

    private ColorNameMapper() {
    }

    public static String mapRgbToColorName(Color color) {
        float red = color.getRed();
        float green = color.getGreen();
        float blue = color.getBlue();

        float r = red / 255f;
        float g = green / 255f;
        float b = blue / 255f;

      
        float brightness = (r + g + b) / 3f;

        if (r > 0.9 && g > 0.9 && b > 0.9) {
            return "White";
        } else if (r < 0.2 && g < 0.2 && b < 0.2) {
            return "Black";
        } else if (Math.abs(r - g) < 0.1 && Math.abs(r - b) < 0.1 && Math.abs(g - b) < 0.1) {
            return "Gray";
        } else if (r > 0.7 && g > 0.5 && b < 0.4) {
            return "Orange";
        } else if (r > 0.7 && g > 0.7 && b < 0.5) {
            return "Yellow";
        } else if (r > 0.8 && g < 0.5 && b > 0.7) {
            return "Pink";
        } else if (r > 0.6 && g < 0.4 && b > 0.6) {
            return "Purple";
        } else if (b > 0.7 && r < 0.5 && g > 0.5) {
            return "Teal";
        } else if (b > 0.6 && r > 0.5 && g > 0.5) {
            return "Light Purple";
        } else if (r > 0.7 && g > 0.6 && b > 0.5) {
            return "Beige";
        } else if (r > 0.5 && g > 0.3 && b < 0.2) {
            return "Brown";
        } else if (g > 0.6 && r > 0.4 && b < 0.4) {
            return "Olive Green";
        } else if (g > 0.7 && b < 0.4 && r < 0.4) {
            if (brightness < 0.4) {
                return "Dark Green";
            }
            return "Green";
        } else if (b > 0.7 && r < 0.4 && g < 0.4) {
            if (brightness < 0.4) {
                return "Navy Blue";
            }
            return "Blue";
        }

        return "Unknown";
    }
}
