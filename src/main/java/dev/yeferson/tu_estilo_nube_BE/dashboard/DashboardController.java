package dev.yeferson.tu_estilo_nube_BE.dashboard;

import dev.yeferson.tu_estilo_nube_BE.category.CategoryCountDTO;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;

    public DashboardController(DashboardService dashboardService, UserService userService) {
        this.dashboardService = dashboardService;
        this.userService = userService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        DashboardStatsDTO stats = dashboardService.getDashboardStats(user);
        return ResponseEntity.ok(stats);
    }

 
    @GetMapping("/categories")
    public ResponseEntity<List<CategorySummaryDTO>> getCategorySummary(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        List<CategorySummaryDTO> summary = dashboardService.getCategorySummary(user);
        return ResponseEntity.ok(summary);
    }
}