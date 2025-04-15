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
import com.google.type.Color; // Clase para el color devuelto
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
            // Candidato alternativo: color no blanco con mayor fracción que supere un umbral
            String candidateColor = null;
            float candidateFraction = 0.0f;

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.println("Error from Vision API: " + res.getError().getMessage());
                    continue;
                }
                // Extraer etiquetas
                res.getLabelAnnotationsList().forEach(annotation -> labels.add(annotation.getDescription()));

                // Extraer propiedades de imagen para obtener datos de color
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
                        
                        String currentColor = mapRgbToColorName(c);
                        if (pixelFraction > maxFraction) {
                            maxFraction = pixelFraction;
                            dominantColor = currentColor;
                        }
                        // Guardamos un candidato no blanco, si lo hay, y con mayor fracción
                        if (!"White".equals(currentColor) && pixelFraction > candidateFraction) {
                            candidateFraction = pixelFraction;
                            candidateColor = currentColor;
                        }
                    }
                    // Si el color dominante es "White" pero hay un candidato con suficiente fracción (por ejemplo, > 0.30)
                    if ("White".equals(dominantColor) && candidateColor != null && candidateFraction > 0.30f) {
                        dominantColor = candidateColor;
                    }
                    System.out.println("Dominant Color Selected: " + dominantColor + " (Fraction: " + maxFraction + ")");
                }
            }
            return new ProcessedImageData(labels, dominantColor);
        }
    }
    
    /**
     * Función helper que mapea los valores RGB a un nombre de color.
     * Se ha ajustado para que, por ejemplo, para (29, 29, 31) retorne "Black".
     */
    public static String mapRgbToColorName(Color color) {
        float red = color.getRed();
        float green = color.getGreen();
        float blue = color.getBlue();
        
        if (red > 240 && green > 240 && blue > 240) {
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
