package com.guma.domain.entities;

import java.util.Objects;

/**
 * Entidad de dominio que representa un rol de usuario en el sistema GUMA.
 * 
 * Mapea directamente con la tabla ROLES de la base de datos.
 * Los roles definen los permisos y accesos que tiene un usuario dentro del
 * sistema.
 * 
 * Esta clase es inmutable para garantizar la integridad de los roles del
 * sistema.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public final class Rol {

    private final Integer idRol;
    private final String nombre;

    /**
     * Constructor completo para crear una instancia de Rol.
     * 
     * @param idRol  identificador único del rol (PK)
     * @param nombre nombre descriptivo del rol (ej: "Usuario", "Admin")
     * @throws IllegalArgumentException si algún parámetro es nulo o el nombre está
     *                                  vacío
     */
    public Rol(Integer idRol, String nombre) {
        if (idRol == null) {
            throw new IllegalArgumentException("El ID del rol no puede ser nulo");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol no puede ser nulo ni vacío");
        }

        this.idRol = idRol;
        this.nombre = nombre.trim();
    }

    /**
     * Obtiene el identificador único del rol.
     * 
     * @return el ID del rol
     */
    public Integer getIdRol() {
        return idRol;
    }

    /**
     * Obtiene el nombre del rol.
     * 
     * @return el nombre del rol
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Verifica si este rol es el rol de administrador.
     * 
     * @return true si el rol es "Admin", false en caso contrario
     */
    public boolean esAdmin() {
        return "Admin".equalsIgnoreCase(nombre);
    }

    /**
     * Verifica si este rol es el rol de usuario estándar.
     * 
     * @return true si el rol es "Usuario", false en caso contrario
     */
    public boolean esUsuarioEstandar() {
        return "Usuario".equalsIgnoreCase(nombre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rol rol = (Rol) o;
        return Objects.equals(idRol, rol.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol);
    }

    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
