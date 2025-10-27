package com.guma.backend.ports;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.guma.domain.entities.Usuario;
import com.guma.domain.valueobjects.Email;

/**
 * Puerto (interface) que define el contrato para el repositorio de usuarios.
 * 
 * Esta interface sigue el patrón Repository y el principio de Inversión de
 * Dependencias.
 * La capa backend define el contrato, pero la implementación está en la capa
 * data.
 * 
 * Permite:
 * - Buscar usuarios por email o ID
 * - Guardar nuevos usuarios
 * - Verificar existencia
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface UsuarioRepository {

    /**
     * Busca un usuario por su dirección de email.
     * 
     * @param email el email del usuario a buscar
     * @return Optional con el usuario si existe, Optional.empty() si no existe
     */
    Optional<Usuario> findByEmail(Email email);

    /**
     * Busca un usuario por su identificador único.
     * 
     * @param idUsuario el ID del usuario a buscar
     * @return Optional con el usuario si existe, Optional.empty() si no existe
     */
    Optional<Usuario> findById(Integer idUsuario);

    /**
     * Guarda un nuevo usuario en el sistema.
     * Este método asigna el ID automáticamente y retorna el usuario con su ID
     * asignado.
     * 
     * @param usuario el usuario a guardar (sin ID)
     * @return el usuario guardado con su ID asignado
     * @throws IllegalArgumentException                             si el usuario es
     *                                                              nulo
     * @throws com.guma.domain.exceptions.UsuarioDuplicadoException si el email ya
     *                                                              existe
     */
    Usuario save(Usuario usuario);

    /**
     * Guarda un nuevo usuario usando una conexión transaccional existente.
     * Versión para uso dentro de transacciones (Unit of Work).
     * 
     * @param usuario el usuario a guardar (sin ID)
     * @param conn    la conexión transaccional a usar
     * @return el usuario guardado con su ID asignado
     * @throws SQLException                                         si ocurre un
     *                                                              error
     *                                                              de base de datos
     * @throws IllegalArgumentException                             si el usuario es
     *                                                              nulo
     * @throws com.guma.domain.exceptions.UsuarioDuplicadoException si el email ya
     *                                                              existe
     */
    Usuario save(Usuario usuario, Connection conn) throws SQLException;

    /**
     * Verifica si existe un usuario con el email especificado.
     * 
     * @param email el email a verificar
     * @return true si existe un usuario con ese email, false en caso contrario
     */
    boolean existsByEmail(Email email);

    /**
     * Actualiza un usuario existente en el sistema.
     * 
     * @param usuario el usuario a actualizar (debe tener ID)
     * @return el usuario actualizado
     * @throws IllegalArgumentException                                si el usuario
     *                                                                 es nulo o no
     *                                                                 tiene ID
     * @throws com.guma.domain.exceptions.EntidadNoEncontradaException si el usuario
     *                                                                 no existe
     */
    Usuario update(Usuario usuario);

    /**
     * Actualiza un usuario existente usando una conexión transaccional.
     * Versión para uso dentro de transacciones (Unit of Work).
     * 
     * @param usuario el usuario a actualizar (debe tener ID)
     * @param conn    la conexión transaccional a usar
     * @return el usuario actualizado
     * @throws SQLException                                            si ocurre un
     *                                                                 error de base
     *                                                                 de
     *                                                                 datos
     * @throws IllegalArgumentException                                si el usuario
     *                                                                 es nulo o no
     *                                                                 tiene ID
     * @throws com.guma.domain.exceptions.EntidadNoEncontradaException si el usuario
     *                                                                 no existe
     */
    Usuario update(Usuario usuario, Connection conn) throws SQLException;
}
