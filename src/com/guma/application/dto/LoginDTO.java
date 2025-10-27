package com.guma.application.dto;

/**
 * DTO para recibir credenciales de inicio de sesión.
 * 
 * Usado en:
 * - Formulario de login (frontend)
 * - AuthFacade.iniciarSesion()
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class LoginDTO {

    private String email;
    private String password;

    /**
     * Constructor vacío.
     */
    public LoginDTO() {
    }

    /**
     * Constructor con parámetros.
     * 
     * @param email    Email del usuario
     * @param password Contraseña
     */
    public LoginDTO(String email, String password) {
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

    @Override
    public String toString() {
        return "LoginDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
