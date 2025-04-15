package dev.yeferson.tu_estilo_nube_BE.image;

import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;
import dev.yeferson.tu_estilo_nube_BE.vision.VisionService;
import dev.yeferson.tu_estilo_nube_BE.category.Category;
import dev.yeferson.tu_estilo_nube_BE.category.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;
    private final VisionService visionService;
    private final CategoryService categoryService;

    public ImageController(ImageService imageService, UserService userService, VisionService visionService, CategoryService categoryService) {
        this.imageService = imageService;
        this.userService = userService;
        this.visionService = visionService;
        this.categoryService = categoryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            Authentication authentication) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(401).body("User not authenticated");
            }

            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            byte[] imageData = file.getBytes();
            // Obtener el objeto que contiene etiquetas y dominantColor
            VisionService.ProcessedImageData processedData = visionService.analyzeImage(imageData);
            List<String> labels = processedData.getLabels();
            String dominantColor = processedData.getDominantColor();

            Category category = null;
            if (categoryId != null) {
                Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
                if (categoryOpt.isPresent()) {
                    category = categoryOpt.get();
                }
            }

            // Se pasa el dominantColor obtenido
            imageService.saveImage(file.getOriginalFilename(), imageData, user, labels, category, dominantColor);
            return ResponseEntity.ok("Image uploaded and analyzed successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ImageDTO>> listImages(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        List<ImageDTO> images = imageService.findByUser(user);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return imageService.findById(id)
                .map(img -> {
                    String contentType = "image/jpeg";
                    if (img.getFileName() != null && img.getFileName().toLowerCase().endsWith(".png")) {
                        contentType = "image/png";
                    }
                    return ResponseEntity.ok()
                            .header("Content-Disposition", "attachment; filename=\"" + img.getFileName() + "\"")
                            .header("Content-Type", contentType)
                            .body(img.getData());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        try {
            imageService.deleteImage(id, user);
            return ResponseEntity.ok("Image deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting image: " + e.getMessage());
        }
    }
}
