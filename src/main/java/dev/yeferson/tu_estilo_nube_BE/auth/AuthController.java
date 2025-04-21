package dev.yeferson.tu_estilo_nube_BE.auth;

import dev.yeferson.tu_estilo_nube_BE.auth.dto.LoginResponseDTO;
import dev.yeferson.tu_estilo_nube_BE.auth.dto.RegisterRequestDTO;
import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;
import dev.yeferson.tu_estilo_nube_BE.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            User user = userService.findByUsername(userDetails.getUsername());
            String token = jwtUtil.generateToken(user.getUsername(), user.getId());
            return ResponseEntity.ok(new LoginResponseDTO(token, user));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // Asignar rol USER por defecto
        Role userRole = new Role();
        userRole.setName("USER");
        newUser.setRoles(Collections.singleton(userRole));

        userService.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
