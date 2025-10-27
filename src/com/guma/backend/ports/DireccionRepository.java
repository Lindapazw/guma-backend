package com.guma.backend.ports;

import com.guma.domain.entities.Direccion;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Puerto (interface) para el repositorio de direcciones.
 * 
 * Define el contrato para operaciones CRUD sobre direcciones.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface DireccionRepository {
    
    /**
     * Guarda una nueva dirección.
     * 
     * @param direccion dirección a guardar
     * @return dirección guardada con ID generado
     * @throws SQLException si ocurre un error de BD
     */
    Direccion save(Direccion direccion) throws SQLException;
    
    /**
     * Busca una dirección por su ID.
     * 
     * @param id identificador de la dirección
     * @return Optional con la dirección si existe
     */
    Optional<Direccion> findById(Integer id);
    
    /**
     * Actualiza una dirección existente.
     * 
     * @param direccion dirección con datos actualizados
     * @return dirección actualizada
     * @throws SQLException si ocurre un error de BD
     */
    Direccion update(Direccion direccion) throws SQLException;
    
    /**
     * Elimina una dirección por su ID.
     * 
     * @param id identificador de la dirección
     * @return true si se eliminó, false si no existía
     * @throws SQLException si ocurre un error de BD
     */
    boolean delete(Integer id) throws SQLException;
}
