package dev.yeferson.tu_estilo_nube_BE.profile.dto;

public class ProfileUpdateDTO {
    private String displayName;
    private String bio;

    public ProfileUpdateDTO() {
    }

    public ProfileUpdateDTO(String displayName, String bio) {
        this.displayName = displayName;
        this.bio = bio;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}