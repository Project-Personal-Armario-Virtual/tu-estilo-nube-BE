package dev.yeferson.tu_estilo_nube_BE.vision;

import com.google.cloud.vision.v1.*;
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
            ByteString imgBytes = ByteString.copyFrom(imageData);
            Image img = Image.newBuilder().setContent(imgBytes).build();

            Feature labelFeature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            Feature propertiesFeature = Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).build();

            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(labelFeature)
                    .addFeatures(propertiesFeature)
                    .setImage(img)
                    .build();

            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));
            List<AnnotateImageResponse> responses = response.getResponsesList();

            List<String> labels = new ArrayList<>();
            String dominantColor = null;
            float maxFraction = -1f;

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.println("Error from Vision API: " + res.getError().getMessage());
                    continue;
                }

                res.getLabelAnnotationsList().forEach(annotation -> labels.add(annotation.getDescription()));

                ImageProperties props = res.getImagePropertiesAnnotation();
                if (props != null && props.getDominantColors() != null) {
                    List<ColorInfo> colors = props.getDominantColors().getColorsList();
                    System.out.println("Detected Colors:");
                    for (ColorInfo colorInfo : colors) {
                        Color c = colorInfo.getColor();
                        float r = c.getRed();
                        float g = c.getGreen();
                        float b = c.getBlue();
                        float pixelFraction = colorInfo.getPixelFraction();

                        String currentColor = ColorNameMapper.mapRgbToColorName(c);

                        System.out.printf("RGB(%.2f, %.2f, %.2f) Fraction: %.2f → %s%n", r, g, b, pixelFraction, currentColor);

                        boolean isWhiteLike = r > 240 && g > 240 && b > 240;

                        if (pixelFraction > maxFraction && !isWhiteLike && !"Unknown".equals(currentColor)) {
                            maxFraction = pixelFraction;
                            dominantColor = currentColor;
                        }
                    }

                    if (dominantColor == null && !colors.isEmpty()) {
                        Color firstColor = colors.get(0).getColor();
                        dominantColor = ColorNameMapper.mapRgbToColorName(firstColor);
                    }

                    System.out.println("Dominant Color Selected: " + dominantColor + " (Fraction: " + maxFraction + ")");
                }
            }
            return new ProcessedImageData(labels, dominantColor);
        }
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
