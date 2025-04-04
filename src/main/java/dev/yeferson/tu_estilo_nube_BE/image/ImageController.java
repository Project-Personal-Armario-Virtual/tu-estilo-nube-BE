package dev.yeferson.tu_estilo_nube_BE.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, Authentication authentication) {
        try {
            String username = authentication.getName(); // Obtiene el usuario del token JWT
            User user = (User) userService.loadUserByUsername(username);
            imageService.saveImage(file.getOriginalFilename(), file.getBytes(), user);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        }
    }



}
