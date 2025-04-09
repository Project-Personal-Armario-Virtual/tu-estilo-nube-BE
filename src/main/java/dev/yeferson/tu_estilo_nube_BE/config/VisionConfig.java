package dev.yeferson.tu_estilo_nube_BE.config;

import com.google.cloud.vision.v1.ImageAnnotatorClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
public class VisionConfig {

    @Bean
    public ImageAnnotatorClient imageAnnotatorClient() throws IOException {
        // Esta llamada utiliza la variable GOOGLE_APPLICATION_CREDENTIALS para cargar las credenciales
        return ImageAnnotatorClient.create();
    }
}