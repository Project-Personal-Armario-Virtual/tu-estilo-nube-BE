package dev.yeferson.tu_estilo_nube_BE.profile.dto;

import dev.yeferson.tu_estilo_nube_BE.profile.Profile;

public class ProfileResponseDTO {
    private Long id;
    private String displayName;
    private String bio;

    public ProfileResponseDTO(Long id, String displayName, String bio) {
        this.id = id;
        this.displayName = displayName;
        this.bio = bio;
    }

    public static ProfileResponseDTO from(Profile profile) {
        return new ProfileResponseDTO(
            profile.getId(),
            profile.getDisplayName(),
            profile.getBio()
        );
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBio() {
        return bio;
    }
}
