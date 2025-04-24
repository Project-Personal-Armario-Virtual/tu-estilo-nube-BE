package dev.yeferson.tu_estilo_nube_BE.auth.dto;

import dev.yeferson.tu_estilo_nube_BE.user.dto.UserResponseDTO;

public class LoginResponseDTO {
    private String token;
    private UserResponseDTO user;

    public LoginResponseDTO(String token, UserResponseDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserResponseDTO getUser() {
        return user;
    }
}