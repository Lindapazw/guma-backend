package com.guma.backend.ports;

import com.guma.domain.entities.*;
import com.guma.domain.valueobjects.*;

import java.util.Optional;

/**
 * Puerto (interface) que define el contrato para el repositorio de roles.
 * 
 * Esta interface permite consultar los roles disponibles en el sistema.
 * Los roles son datos de catálogo que generalmente no se modifican.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface RolRepository {

    /**
     * Busca un rol por su identificador único.
     * 
     * @param idRol el ID del rol a buscar
     * @return Optional con el rol si existe, Optional.empty() si no existe
     */
    Optional<Rol> findById(Integer idRol);

    /**
     * Busca un rol por su nombre.
     * 
     * @param nombre el nombre del rol (ej: "Usuario", "Admin")
     * @return Optional con el rol si existe, Optional.empty() si no existe
     */
    Optional<Rol> findByNombre(String nombre);

    /**
     * Obtiene el rol por defecto para nuevos usuarios registrados.
     * Típicamente es el rol "Usuario" con ID 3.
     * 
     * @return el rol por defecto del sistema
     * @throws com.guma.domain.exceptions.EntidadNoEncontradaException si no existe
     *                                                                 el rol por
     *                                                                 defecto
     */
    Rol getRolPorDefecto();
}
