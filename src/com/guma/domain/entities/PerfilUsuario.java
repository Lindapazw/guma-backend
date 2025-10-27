package com.guma.domain.entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad de dominio que representa el perfil personal de un usuario en GUMA.
 * 
 * Mapea directamente con la tabla PERFIL_USUARIOS de la base de datos.
 * Contiene información personal y de contacto del usuario.
 * 
 * Esta entidad se relaciona 1:1 con Usuario mediante id_usuario (FK).
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilUsuario {

    private Integer idPerfilUsuario;
    private Integer idUsuario;
    private Integer idSexo;
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private Integer idDireccion;
    private Integer idRedSocial;
    private Integer fotoPerfil;
    private boolean verificado;

    /**
     * Constructor completo para crear una instancia de PerfilUsuario.
     * 
     * @param idPerfilUsuario identificador único del perfil (puede ser null para
     *                        nuevos)
     * @param idUsuario       identificador del usuario asociado (FK a USUARIOS)
     * @param idSexo          identificador del sexo (FK a SEXOS)
     * @param dni             documento nacional de identidad (único)
     * @param nombre          nombre del usuario
     * @param apellido        apellido del usuario
     * @param fechaNacimiento fecha de nacimiento
     * @param email           email de contacto
     * @param telefono        teléfono de contacto (opcional)
     * @param idDireccion     identificador de la dirección (FK a DIRECCIONES)
     * @param idRedSocial     identificador de red social (FK a REDES_SOCIALES,
     *                        opcional)
     * @param fotoPerfil      identificador de la foto (FK a IMAGES, opcional)
     * @param verificado      indica si el perfil fue verificado
     */
    public PerfilUsuario(Integer idPerfilUsuario, Integer idUsuario, Integer idSexo,
            String dni, String nombre, String apellido, LocalDate fechaNacimiento,
            String email, String telefono, Integer idDireccion, Integer idRedSocial,
            Integer fotoPerfil, boolean verificado) {
        this.idPerfilUsuario = idPerfilUsuario;
        this.setIdUsuario(idUsuario);
        this.setIdSexo(idSexo);
        this.setDni(dni);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setFechaNacimiento(fechaNacimiento);
        this.setEmail(email);
        this.telefono = telefono;
        this.idDireccion = idDireccion;
        this.idRedSocial = idRedSocial;
        this.fotoPerfil = fotoPerfil;
        this.verificado = verificado;
    }

    /**
     * Constructor simplificado para crear un nuevo perfil (campos obligatorios).
     * 
     * @param idUsuario       identificador del usuario asociado
     * @param idSexo          identificador del sexo
     * @param dni             documento nacional de identidad
     * @param nombre          nombre del usuario
     * @param apellido        apellido del usuario
     * @param fechaNacimiento fecha de nacimiento
     * @param email           email de contacto
     */
    public PerfilUsuario(Integer idUsuario, Integer idSexo, String dni, String nombre,
            String apellido, LocalDate fechaNacimiento, String email) {
        this(null, idUsuario, idSexo, dni, nombre, apellido, fechaNacimiento,
                email, null, null, null, null, false);
    }

    /**
     * Marca el perfil como verificado.
     */
    public void verificar() {
        this.verificado = true;
    }

    /**
     * Verifica si el perfil está completo (tiene todos los datos obligatorios).
     * 
     * @return true si el perfil está completo, false en caso contrario
     */
    public boolean estaCompleto() {
        return idUsuario != null &&
                idSexo != null &&
                dni != null && !dni.trim().isEmpty() &&
                nombre != null && !nombre.trim().isEmpty() &&
                apellido != null && !apellido.trim().isEmpty() &&
                fechaNacimiento != null &&
                email != null && !email.trim().isEmpty();
    }

    /**
     * Obtiene el nombre completo del usuario (nombre + apellido).
     * 
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
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
        if (idUsuario == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo");
        }
        this.idUsuario = idUsuario;
    }

    public Integer getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Integer idSexo) {
        if (idSexo == null) {
            throw new IllegalArgumentException("El ID de sexo no puede ser nulo");
        }
        this.idSexo = idSexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        // DNI puede ser NULL durante el registro inicial
        // El usuario debe completarlo más adelante
        if (dni != null && dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser una cadena vacía");
        }
        this.dni = dni != null ? dni.trim() : null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo ni vacío");
        }
        this.apellido = apellido.trim();
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo ni vacío");
        }
        this.email = email.trim().toLowerCase();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono != null ? telefono.trim() : null;
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

    public Integer getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Integer fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PerfilUsuario that = (PerfilUsuario) o;
        return Objects.equals(idPerfilUsuario, that.idPerfilUsuario) &&
                Objects.equals(dni, that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerfilUsuario, dni);
    }

    @Override
    public String toString() {
        return "PerfilUsuario{" +
                "idPerfilUsuario=" + idPerfilUsuario +
                ", idUsuario=" + idUsuario +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", verificado=" + verificado +
                '}';
    }
}
