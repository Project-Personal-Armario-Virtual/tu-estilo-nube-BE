package dev.yeferson.tu_estilo_nube_BE.auth;

import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            User user = userService.findByUsername(userDetails.getUsername());
            String token = jwtUtil.generateToken(user.getUsername(), user.getId());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
