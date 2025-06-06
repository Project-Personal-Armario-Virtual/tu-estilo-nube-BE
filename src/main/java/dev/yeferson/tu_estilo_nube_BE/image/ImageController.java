package dev.yeferson.tu_estilo_nube_BE.image;

import dev.yeferson.tu_estilo_nube_BE.category.Category;
import dev.yeferson.tu_estilo_nube_BE.category.rules.CategoryMappingService;
import dev.yeferson.tu_estilo_nube_BE.category.CategoryService;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;
import dev.yeferson.tu_estilo_nube_BE.vision.VisionService;
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

    public ImageController(ImageService imageService, UserService userService, VisionService visionService,
            CategoryService categoryService) {
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

            VisionService.ProcessedImageData processedData = visionService.analyzeImage(imageData);
            List<String> labels = processedData.getLabels();
            String dominantColor = processedData.getDominantColor();

            Category category = null;

            if (categoryId == null) {
                CategoryMappingService mappingService = new CategoryMappingService();
                String suggestedCategoryName = mappingService.suggestCategory(processedData);
                Optional<Category> categoryOptional = categoryService.findByName(suggestedCategoryName);
                if (categoryOptional.isPresent()) {
                    category = categoryOptional.get();
                } else {
                    category = new Category();
                    category.setName(suggestedCategoryName);
                    category = categoryService.save(category);
                }
            } else {
                Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
                if (categoryOpt.isPresent()) {
                    category = categoryOpt.get();
                }
            }

            imageService.saveImage(file.getOriginalFilename(), imageData, user, labels, category, dominantColor);
            String responseMessage = "Image uploaded and analyzed successfully. Suggested category: "
                    + (category != null ? category.getName() : "None");
            return ResponseEntity.ok(responseMessage);
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

    @GetMapping("/{id}/preview")
    public ResponseEntity<byte[]> previewImage(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        return imageService.findByIdAndUser(id, user)
                .map(img -> {
                    String contentType = "image/jpeg";
                    String filename = img.getFileName() != null ? img.getFileName().toLowerCase() : "";
                    if (filename.endsWith(".png"))
                        contentType = "image/png";
                    if (filename.endsWith(".webp"))
                        contentType = "image/webp";

                    return ResponseEntity.ok()
                            .header("Content-Type", contentType)
                            .body(img.getData());
                })
                .orElseGet(() -> ResponseEntity.status(404).build());
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
        } catch (ImageInUseException e) {
          
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (RuntimeException e) {
         
            return ResponseEntity.status(400).body("Bad Request: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting image: " + e.getMessage());
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ImageDTO>> getRecentImages(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        List<Image> recentImages = imageService.findRecentImagesByUser(user);
        List<ImageDTO> imageDTOs = recentImages.stream().map(i -> {
            String categoryName = (i.getCategory() != null) ? i.getCategory().getName() : null;
            return new ImageDTO(i.getId(), i.getFileName(), i.getUser().getId(), i.getLabels(), categoryName,
                    i.getDominantColor());
        }).toList();

        return ResponseEntity.ok(imageDTOs);
    }

}
