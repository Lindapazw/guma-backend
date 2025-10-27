package com.guma.domain.enums;

import com.guma.domain.valueobjects.Email;
import com.guma.domain.valueobjects.Password;

/**
 * Enumeración que representa los estados posibles de un usuario en el sistema
 * GUMA.
 * 
 * Los estados controlan el ciclo de vida y las restricciones de acceso de un
 * usuario.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public enum EstadoUsuario {

    /**
     * Usuario activo con acceso completo al sistema.
     */
    ACTIVO("Activo", "Usuario con acceso completo al sistema"),

    /**
     * Usuario temporalmente inactivo, puede reactivarse.
     */
    INACTIVO("Inactivo", "Usuario temporalmente sin acceso"),

    /**
     * Usuario bloqueado por seguridad o políticas del sistema.
     */
    BLOQUEADO("Bloqueado", "Usuario bloqueado por políticas de seguridad"),

    /**
     * Usuario marcado como eliminado (baja lógica).
     */
    ELIMINADO("Eliminado", "Usuario dado de baja del sistema");

    private final String nombre;
    private final String descripcion;

    /**
     * Constructor del enum.
     * 
     * @param nombre      nombre descriptivo del estado
     * @param descripcion descripción detallada del estado
     */
    EstadoUsuario(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nombre descriptivo del estado.
     * 
     * @return el nombre del estado
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripción detallada del estado.
     * 
     * @return la descripción del estado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Verifica si el usuario puede acceder al sistema.
     * 
     * @return true si el estado permite acceso (ACTIVO), false en caso contrario
     */
    public boolean puedeAcceder() {
        return this == ACTIVO;
    }

    /**
     * Verifica si el usuario está bloqueado.
     * 
     * @return true si está bloqueado, false en caso contrario
     */
    public boolean estaBloqueado() {
        return this == BLOQUEADO;
    }

    /**
     * Verifica si el usuario fue eliminado.
     * 
     * @return true si fue eliminado, false en caso contrario
     */
    public boolean estaEliminado() {
        return this == ELIMINADO;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
