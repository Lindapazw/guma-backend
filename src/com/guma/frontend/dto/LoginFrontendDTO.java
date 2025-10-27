package com.guma.frontend.dto;

/**
 * DTO para login de usuario
 * Usado en RF-02
 */
public class LoginFrontendDTO {
    private String email;
    private String password;

    // Constructor vacío
    public LoginFrontendDTO() {
    }

    // Constructor completo
    public LoginFrontendDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
