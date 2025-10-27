package com.guma.frontend.dto;

import java.time.LocalDateTime;

/**
 * DTO de usuario (sin contraseña)
 * Se retorna después de registro o login exitoso
 */
public class UsuarioFrontendDTO {
    private int id;
    private String email;
    private String nombre;
    private String apellido;
    private String telefono;
    private String rolNombre;
    private String estado;
    private LocalDateTime fechaCreacion;

    // Constructor vacío
    public UsuarioFrontendDTO() {
    }

    // Constructor completo
    public UsuarioFrontendDTO(int id, String email, String nombre, String apellido, 
                     String telefono, String rolNombre, String estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.rolNombre = rolNombre;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
