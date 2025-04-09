package dev.yeferson.tu_estilo_nube_BE.image;

import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;
import dev.yeferson.tu_estilo_nube_BE.vision.VisionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;
    private final VisionService visionService;

    public ImageController(ImageService imageService, UserService userService, VisionService visionService) {
        this.imageService = imageService;
        this.userService = userService;
        this.visionService = visionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, Authentication authentication) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(401).body("User not authenticated");
            }

            String username = authentication.getName();
            System.out.println("Authenticated user: " + username);
            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            System.out.println("Saving image: " + file.getOriginalFilename());
            byte[] imageData = file.getBytes();

          
            List<String> labels = visionService.analyzeImage(imageData);

         
            imageService.saveImage(file.getOriginalFilename(), imageData, user, labels);
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
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<ImageDTO> images = imageService.findByUser(user);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + image.getFileName())
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getData());
    }
}
