package com.guma.application.dto;

import java.time.LocalDate;

/**
 * DTO para recibir datos de registro de un nuevo usuario.
 * Contiene los campos necesarios para el registro completo.
 * 
 * Usado en:
 * - Formulario de registro (frontend)
 * - AuthFacade.registrarUsuario()
 * 
 * @author GUMA Development Team
 * @version 1.2 - Agregada fecha de nacimiento
 */
public class RegistroUsuarioDTO {

    private String email;
    private String password;
    private String confirmarPassword;
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fechaNacimiento;

    /**
     * Constructor vacío.
     */
    public RegistroUsuarioDTO() {
    }

    /**
     * Constructor con parámetros.
     * 
     * @param email             Email del usuario
     * @param password          Contraseña
     * @param confirmarPassword Confirmación de contraseña
     * @param nombre            Nombre del usuario
     * @param apellido          Apellido del usuario
     * @param telefono          Teléfono del usuario (opcional)
     */
    public RegistroUsuarioDTO(String email, String password, String confirmarPassword,
            String nombre, String apellido, String telefono) {
        this.email = email;
        this.password = password;
        this.confirmarPassword = confirmarPassword;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
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

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
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

    @Override
    public String toString() {
        return "RegistroUsuarioDTO{" +
                "email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
