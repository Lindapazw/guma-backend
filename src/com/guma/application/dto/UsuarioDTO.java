package com.guma.application.dto;

import java.time.LocalDateTime;

/**
 * DTO para transferir información de Usuario entre capas.
 * Versión simplificada de la entidad Usuario sin lógica de dominio.
 * Incluye datos del perfil para mostrar en UI.
 * 
 * Usado para:
 * - Mostrar datos de usuario en la UI
 * - Respuestas de login
 * - Consultas de usuario
 * 
 * @author GUMA Development Team
 * @version 1.1 - Agregados nombre, apellido, telefono y estado
 */
public class UsuarioDTO {

    private Integer idUsuario;
    private String email;
    private Integer idRol;
    private String nombreRol;
    private boolean verificado;
    private LocalDateTime ultimaConexion;

    // Datos del perfil (si existe)
    private String nombre;
    private String apellido;
    private String telefono;
    private String estado; // "activo", "inactivo", "bloqueado"

    /**
     * Constructor vacío.
     */
    public UsuarioDTO() {
    }

    /**
     * Constructor completo.
     */
    public UsuarioDTO(Integer idUsuario, String email, Integer idRol,
            String nombreRol, boolean verificado, LocalDateTime ultimaConexion,
            String nombre, String apellido, String telefono, String estado) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.verificado = verificado;
        this.ultimaConexion = ultimaConexion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.estado = estado;
    }

    // Getters y Setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public LocalDateTime getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(LocalDateTime ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario=" + idUsuario +
                ", email='" + email + '\'' +
                ", nombreRol='" + nombreRol + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", estado='" + estado + '\'' +
                ", verificado=" + verificado +
                '}';
    }
}
