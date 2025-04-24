package dev.yeferson.tu_estilo_nube_BE.profile;

import dev.yeferson.tu_estilo_nube_BE.profile.dto.ProfileResponseDTO;
import dev.yeferson.tu_estilo_nube_BE.profile.dto.ProfileUpdateDTO;
import dev.yeferson.tu_estilo_nube_BE.security.JwtUtil;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ProfileService profileService;

    public ProfileController(JwtUtil jwtUtil, UserService userService, ProfileService profileService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping("/me")
public ResponseEntity<ProfileResponseDTO> getProfile(Authentication authentication) {
    User user = userService.findByUsername(authentication.getName());
    Profile profile = profileService.findByUser(user);

    ProfileResponseDTO response = new ProfileResponseDTO(
        profile.getId(),
        profile.getDisplayName(),
        profile.getBio()
    );

    return ResponseEntity.ok(response);
}

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(
            HttpServletRequest request,
            @RequestBody ProfileUpdateDTO updateDTO) {
        Long userId = jwtUtil.getUserIdFromRequest(request);
        User user = userService.findById(userId);

        Profile updated = profileService.updateProfile(user, updateDTO.getDisplayName(), updateDTO.getBio());

        ProfileResponseDTO responseDTO = new ProfileResponseDTO(
                updated.getId(),
                updated.getDisplayName(),
                updated.getBio());

        return ResponseEntity.ok(responseDTO);
    }
}
