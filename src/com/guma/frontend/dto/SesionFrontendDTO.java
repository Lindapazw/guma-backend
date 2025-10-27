package com.guma.frontend.dto;

import java.time.LocalDateTime;

/**
 * DTO de sesión
 * Retornado después de login exitoso
 */
public class SesionFrontendDTO {
    private int usuarioId;
    private String email;
    private String nombreCompleto;
    private String rolNombre;
    private String token;
    private LocalDateTime expiracion;
    private UsuarioFrontendDTO usuario;

    // Constructor vacío
    public SesionFrontendDTO() {
    }

    // Constructor completo
    public SesionFrontendDTO(int usuarioId, String email, String nombreCompleto, 
                    String rolNombre, String token, LocalDateTime expiracion) {
        this.usuarioId = usuarioId;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rolNombre = rolNombre;
        this.token = token;
        this.expiracion = expiracion;
    }

    // Getters y Setters
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(LocalDateTime expiracion) {
        this.expiracion = expiracion;
    }
    
    public UsuarioFrontendDTO getUsuario() {
        return usuario;
    }
    
    public void setUsuario(UsuarioFrontendDTO usuario) {
        this.usuario = usuario;
    }
}
