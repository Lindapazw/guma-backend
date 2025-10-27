package com.guma.frontend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para el perfil de usuario
 * Mapea la tabla PERFIL_USUARIOS y datos relacionados de USUARIOS
 */
public class PerfilUsuarioFrontendDTO {
    private Long id;
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private Integer dni;
    private String email;
    private String telefono;
    private Integer idSexo;
    private String sexoDescripcion; // Para mostrar en UI
    private Integer idDireccion;
    private String direccionCompleta; // Para mostrar en UI
    private Integer idRedSocial;
    private String redSocialDescripcion; // Para mostrar en UI
    private String urlRed; // URL personal del usuario para esa red social
    private Integer fotoPerfil;
    private String fotoPerfilUrl; // URL de la imagen
    private LocalDate fechaNacimiento;
    private boolean verificado;
    
    // Campos adicionales de USUARIOS
    private Integer idRol;
    private String rolNombre; // Para mostrar en UI
    private LocalDateTime ultimaConexion;
    
    // Constructor vacío
    public PerfilUsuarioFrontendDTO() {
    }
    
    // Constructor con campos obligatorios
    public PerfilUsuarioFrontendDTO(String nombre, String apellido, Integer dni, String email, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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
    
    public Integer getDni() {
        return dni;
    }
    
    public void setDni(Integer dni) {
        this.dni = dni;
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
    
    public Integer getIdSexo() {
        return idSexo;
    }
    
    public void setIdSexo(Integer idSexo) {
        this.idSexo = idSexo;
    }
    
    public String getSexoDescripcion() {
        return sexoDescripcion;
    }
    
    public void setSexoDescripcion(String sexoDescripcion) {
        this.sexoDescripcion = sexoDescripcion;
    }
    
    public Integer getIdDireccion() {
        return idDireccion;
    }
    
    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }
    
    public String getDireccionCompleta() {
        return direccionCompleta;
    }
    
    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }
    
    public Integer getIdRedSocial() {
        return idRedSocial;
    }
    
    public void setIdRedSocial(Integer idRedSocial) {
        this.idRedSocial = idRedSocial;
    }
    
    public String getRedSocialDescripcion() {
        return redSocialDescripcion;
    }
    
    public void setRedSocialDescripcion(String redSocialDescripcion) {
        this.redSocialDescripcion = redSocialDescripcion;
    }
    
    public String getUrlRed() {
        return urlRed;
    }
    
    public void setUrlRed(String urlRed) {
        this.urlRed = urlRed;
    }
    
    public Integer getFotoPerfil() {
        return fotoPerfil;
    }
    
    public void setFotoPerfil(Integer fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public boolean isVerificado() {
        return verificado;
    }
    
    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
    
    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }
    
    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
    
    public Integer getIdRol() {
        return idRol;
    }
    
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    
    public String getRolNombre() {
        return rolNombre;
    }
    
    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }
    
    public LocalDateTime getUltimaConexion() {
        return ultimaConexion;
    }
    
    public void setUltimaConexion(LocalDateTime ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }
    
    @Override
    public String toString() {
        return "PerfilUsuarioFrontendDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", email='" + email + '\'' +
                '}';
    }
}
