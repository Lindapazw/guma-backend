package com.guma.frontend.dto;

import java.time.LocalDate;

/**
 * DTO para registro de usuario
 * Usado en RF-01
 */
public class RegistroFrontendDTO {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fechaNacimiento;

    // Constructor vacío
    public RegistroFrontendDTO() {
    }

    // Constructor completo
    public RegistroFrontendDTO(String email, String password, String nombre, String apellido, 
                             String telefono, LocalDate fechaNacimiento) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
