package com.guma.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import com.guma.domain.valueobjects.Email;
import com.guma.domain.valueobjects.Password;

/**
 * Entidad de dominio que representa un usuario del sistema GUMA.
 * 
 * Mapea directamente con la tabla USUARIOS de la base de datos.
 * Un usuario es la entidad de autenticación principal del sistema.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class Usuario {

    private Integer idUsuario;
    private Email email;
    private Password password;
    private Integer idRol;
    private boolean verified;
    private LocalDateTime ultimaConexion;

    /**
     * Constructor completo para crear una instancia de Usuario.
     * 
     * @param idUsuario      identificador único del usuario (puede ser null para
     *                       nuevos usuarios)
     * @param email          dirección de correo electrónico (value object validado)
     * @param password       contraseña hasheada (value object)
     * @param idRol          identificador del rol asignado (FK a ROLES)
     * @param verified       indica si el email fue verificado
     * @param ultimaConexion fecha y hora de la última conexión (puede ser null)
     */
    public Usuario(Integer idUsuario, Email email, Password password,
            Integer idRol, boolean verified, LocalDateTime ultimaConexion) {
        this.idUsuario = idUsuario;
        this.setEmail(email);
        this.setPassword(password);
        this.setIdRol(idRol);
        this.verified = verified;
        this.ultimaConexion = ultimaConexion;
    }

    /**
     * Constructor para crear un nuevo usuario (sin ID asignado aún).
     * El ID se asignará automáticamente por la base de datos.
     * Por defecto el usuario se crea con verified=true.
     * (En futuras versiones se implementará verificación de email)
     * 
     * @param email    dirección de correo electrónico
     * @param password contraseña hasheada
     * @param idRol    identificador del rol
     */
    public Usuario(Email email, Password password, Integer idRol) {
        this(null, email, password, idRol, true, null);
    }

    /**
     * Actualiza la fecha de última conexión a la fecha actual.
     */
    public void actualizarUltimaConexion() {
        this.ultimaConexion = LocalDateTime.now();
    }

    /**
     * Marca el email del usuario como verificado.
     */
    public void verificarEmail() {
        this.verified = true;
    }

    /**
     * Verifica si el email del usuario está verificado.
     * 
     * @return true si está verificado, false en caso contrario
     */
    public boolean estaVerificado() {
        return verified;
    }

    // Getters y Setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser nulo");
        }
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        if (password == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }
        this.password = password;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        if (idRol == null) {
            throw new IllegalArgumentException("El ID del rol no puede ser nulo");
        }
        this.idRol = idRol;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(LocalDateTime ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario) &&
                Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", email=" + email +
                ", idRol=" + idRol +
                ", verified=" + verified +
                ", ultimaConexion=" + ultimaConexion +
                '}';
    }
}
