package dev.yeferson.tu_estilo_nube_BE.auth;

import dev.yeferson.tu_estilo_nube_BE.auth.dto.LoginResponseDTO;
import dev.yeferson.tu_estilo_nube_BE.auth.dto.RegisterRequestDTO;
import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;
import dev.yeferson.tu_estilo_nube_BE.user.dto.UserResponseDTO;
import dev.yeferson.tu_estilo_nube_BE.role.Role;
import dev.yeferson.tu_estilo_nube_BE.role.RoleRepository;
import dev.yeferson.tu_estilo_nube_BE.profile.Profile;
import dev.yeferson.tu_estilo_nube_BE.profile.ProfileRepository;

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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            User user = userService.findByUsername(userDetails.getUsername());
            String token = jwtUtil.generateToken(user.getUsername(), user.getId());

            UserResponseDTO userDTO = new UserResponseDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail());

            return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
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

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        newUser.setRoles(Collections.singleton(userRole));
        User savedUser = userService.save(newUser);

        Profile profile = new Profile();
        profile.setUser(savedUser);
        profile.setDisplayName(savedUser.getUsername());
        profile.setBio("");
        profileRepository.save(profile);

        return ResponseEntity.ok("User registered successfully");
    }
}
