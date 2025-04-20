package dev.yeferson.tu_estilo_nube_BE.vision;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.ColorInfo;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageProperties;
import com.google.protobuf.ByteString;
import com.google.type.Color;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisionService {

    public ProcessedImageData analyzeImage(byte[] imageData) throws IOException {
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            // Convertir la imagen a ByteString
            ByteString imgBytes = ByteString.copyFrom(imageData);
            Image img = Image.newBuilder().setContent(imgBytes).build();

            // Configurar las features: etiquetas y propiedades de imagen
            Feature labelFeature = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            Feature propertiesFeature = Feature.newBuilder().setType(Type.IMAGE_PROPERTIES).build();

            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(labelFeature)
                    .addFeatures(propertiesFeature)
                    .setImage(img)
                    .build();

            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));
            List<AnnotateImageResponse> responses = response.getResponsesList();

            List<String> labels = new ArrayList<>();
            String dominantColor = null;
            float maxFraction = 0.0f;

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.println("Error from Vision API: " + res.getError().getMessage());
                    continue;
                }
                // Extraer etiquetas
                res.getLabelAnnotationsList().forEach(annotation -> labels.add(annotation.getDescription()));

                // Extraer propiedades de imagen para obtener los datos de color
                ImageProperties properties = res.getImagePropertiesAnnotation();
                if (properties != null && properties.getDominantColors() != null) {
                    List<ColorInfo> colors = properties.getDominantColors().getColorsList();
                    System.out.println("Detected Colors:");
                    for (ColorInfo colorInfo : colors) {
                        Color c = colorInfo.getColor();
                        float r = c.getRed();
                        float g = c.getGreen();
                        float b = c.getBlue();
                        float pixelFraction = colorInfo.getPixelFraction();
                        System.out.printf("RGB(%.2f, %.2f, %.2f), Fraction: %.2f%n", r, g, b, pixelFraction);

                        // Filtrar colores casi blancos para ignorarlos (umbral ajustado a 220)
                        if (r > 220 && g > 220 && b > 220) {
                            System.out.println("Ignoring nearly white color");
                            continue;
                        }

                        String currentColor = mapRgbToColorName(c);
                        if (pixelFraction > maxFraction) {
                            maxFraction = pixelFraction;
                            dominantColor = currentColor;
                        }
                    }
                    // Fallback: si no se asignó color, se usa el primer color disponible
                    if (dominantColor == null && !colors.isEmpty()) {
                        Color firstColor = colors.get(0).getColor();
                        dominantColor = mapRgbToColorName(firstColor);
                        maxFraction = colors.get(0).getPixelFraction();
                    }
                    System.out.println("Dominant Color Selected: " + dominantColor + " (Fraction: " + maxFraction + ")");
                }
            }
            return new ProcessedImageData(labels, dominantColor);
        }
    }

    /**
     * Función helper que mapea los valores RGB a un nombre de color.
     * Se ajustó el umbral para "White" a 220 para captar prendas blancas correctamente.
     */
    public static String mapRgbToColorName(Color color) {
        float red = color.getRed();
        float green = color.getGreen();
        float blue = color.getBlue();

        if (red > 220 && green > 220 && blue > 220) {
            return "White";
        } else if (red < 70 && green < 70 && blue < 70) {
            return "Black";
        } else if (red >= green && red >= blue) {
            return "Red";
        } else if (green >= red && green >= blue) {
            return "Green";
        } else if (blue >= red && blue >= green) {
            return "Blue";
        }
        return "Unknown";
    }

    public static class ProcessedImageData {
        private final List<String> labels;
        private final String dominantColor;

        public ProcessedImageData(List<String> labels, String dominantColor) {
            this.labels = labels;
            this.dominantColor = dominantColor;
        }

        public List<String> getLabels() {
            return labels;
        }

        public String getDominantColor() {
            return dominantColor;
        }
    }
}
