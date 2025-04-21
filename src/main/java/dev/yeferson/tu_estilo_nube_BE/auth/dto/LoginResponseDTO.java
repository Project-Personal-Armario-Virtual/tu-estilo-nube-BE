package dev.yeferson.tu_estilo_nube_BE.auth.dto;

import dev.yeferson.tu_estilo_nube_BE.user.User;

public class LoginResponseDTO {

    private String token;
    private User user;

    public LoginResponseDTO(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}