package com.guma.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para transferir información de Perfil de Usuario entre capas.
 * Versión simplificada de la entidad PerfilUsuario.
 * 
 * Usado para:
 * - Mostrar perfil en la UI
 * - Actualizar datos de perfil
 * - Consultas de perfil
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilUsuarioDTO {

    private Integer idPerfilUsuario;
    private Integer idUsuario;
    private Integer idSexo;
    private String nombreSexo;
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private Integer idDireccion;
    private Integer idRedSocial;
    private Integer fotoPerfilId;
    private String fotoPerfilUrl; // URL/path de la foto
    private boolean verificado;
    private LocalDateTime ultimaConexion; // Campo del usuario relacionado

    /**
     * Constructor vacío.
     */
    public PerfilUsuarioDTO() {
    }

    /**
     * Constructor completo.
     */
    public PerfilUsuarioDTO(Integer idPerfilUsuario, Integer idUsuario, Integer idSexo,
            String nombreSexo, String dni, String nombre, String apellido,
            LocalDate fechaNacimiento, String email, String telefono,
            Integer idDireccion, Integer idRedSocial, Integer fotoPerfilId,
            boolean verificado) {
        this.idPerfilUsuario = idPerfilUsuario;
        this.idUsuario = idUsuario;
        this.idSexo = idSexo;
        this.nombreSexo = nombreSexo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.idDireccion = idDireccion;
        this.idRedSocial = idRedSocial;
        this.fotoPerfilId = fotoPerfilId;
        this.verificado = verificado;
    }

    // Getters y Setters

    public Integer getIdPerfilUsuario() {
        return idPerfilUsuario;
    }

    public void setIdPerfilUsuario(Integer idPerfilUsuario) {
        this.idPerfilUsuario = idPerfilUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Integer idSexo) {
        this.idSexo = idSexo;
    }

    public String getNombreSexo() {
        return nombreSexo;
    }

    public void setNombreSexo(String nombreSexo) {
        this.nombreSexo = nombreSexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Integer getIdRedSocial() {
        return idRedSocial;
    }

    public void setIdRedSocial(Integer idRedSocial) {
        this.idRedSocial = idRedSocial;
    }

    public Integer getFotoPerfilId() {
        return fotoPerfilId;
    }

    public void setFotoPerfilId(Integer fotoPerfilId) {
        this.fotoPerfilId = fotoPerfilId;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
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

    /**
     * Verifica si el perfil está completo (todos los campos obligatorios llenos).
     * 
     * @return true si el perfil está completo
     */
    public boolean estaCompleto() {
        return dni != null && !dni.isEmpty()
                && nombre != null && !nombre.isEmpty()
                && apellido != null && !apellido.isEmpty()
                && fechaNacimiento != null
                && email != null && !email.isEmpty()
                && idSexo != null;
    }

    @Override
    public String toString() {
        return "PerfilUsuarioDTO{" +
                "idPerfilUsuario=" + idPerfilUsuario +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", verificado=" + verificado +
                '}';
    }
}
