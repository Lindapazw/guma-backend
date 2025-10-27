package com.guma.application.dto;

import java.time.LocalDateTime;

/**
 * DTO para representar la sesión activa de un usuario.
 * Se retorna tras un login exitoso y contiene toda la información
 * necesaria para la interfaz de usuario.
 * 
 * Usado en:
 * - Respuesta de AuthFacade.iniciarSesion()
 * - SesionActual (singleton del frontend)
 * - MainFrame para mostrar datos del usuario logueado
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class SesionDTO {

    private Integer usuarioId;
    private String email;
    private String nombreCompleto;
    private String nombre;
    private String apellido;
    private String telefono;
    private String rolNombre;
    private String estado;
    private boolean verificado;
    private String token; // Para futuras implementaciones
    private LocalDateTime expiracion; // Para futuras implementaciones
    private LocalDateTime ultimaConexion;

    /**
     * Constructor vacío.
     */
    public SesionDTO() {
    }

    /**
     * Constructor completo.
     * 
     * @param usuarioId      ID del usuario
     * @param email          Email del usuario
     * @param nombreCompleto Nombre completo (nombre + apellido)
     * @param nombre         Nombre
     * @param apellido       Apellido
     * @param telefono       Teléfono
     * @param rolNombre      Nombre del rol ("Usuario", "Admin")
     * @param estado         Estado de la cuenta ("activo", "inactivo")
     * @param verificado     Si el email está verificado
     * @param token          Token de sesión (opcional)
     * @param expiracion     Fecha de expiración del token (opcional)
     * @param ultimaConexion Última fecha de conexión
     */
    public SesionDTO(Integer usuarioId, String email, String nombreCompleto,
            String nombre, String apellido, String telefono,
            String rolNombre, String estado, boolean verificado,
            String token, LocalDateTime expiracion, LocalDateTime ultimaConexion) {
        this.usuarioId = usuarioId;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.rolNombre = rolNombre;
        this.estado = estado;
        this.verificado = verificado;
        this.token = token;
        this.expiracion = expiracion;
        this.ultimaConexion = ultimaConexion;
    }

    // Getters y Setters

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
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

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
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

    public LocalDateTime getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(LocalDateTime ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    @Override
    public String toString() {
        return "SesionDTO{" +
                "usuarioId=" + usuarioId +
                ", email='" + email + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", rolNombre='" + rolNombre + '\'' +
                ", estado='" + estado + '\'' +
                ", verificado=" + verificado +
                '}';
    }
}
