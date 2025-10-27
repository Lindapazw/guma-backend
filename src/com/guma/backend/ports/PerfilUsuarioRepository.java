package com.guma.backend.ports;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.guma.domain.entities.PerfilUsuario;

/**
 * Puerto (interface) que define el contrato para el repositorio de perfiles de
 * usuario.
 * 
 * Esta interface permite gestionar la información personal de los usuarios.
 * Cada usuario puede tener un solo perfil (relación 1:1).
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface PerfilUsuarioRepository {

    /**
     * Busca un perfil de usuario por su identificador único.
     * 
     * @param idPerfilUsuario el ID del perfil a buscar
     * @return Optional con el perfil si existe, Optional.empty() si no existe
     */
    Optional<PerfilUsuario> findById(Integer idPerfilUsuario);

    /**
     * Busca un perfil por el ID del usuario asociado.
     * 
     * @param idUsuario el ID del usuario
     * @return Optional con el perfil si existe, Optional.empty() si no existe
     */
    Optional<PerfilUsuario> findByUsuarioId(Integer idUsuario);

    /**
     * Busca un perfil por el DNI.
     * 
     * @param dni el documento nacional de identidad
     * @return Optional con el perfil si existe, Optional.empty() si no existe
     */
    Optional<PerfilUsuario> findByDni(String dni);

    /**
     * Guarda un nuevo perfil de usuario en el sistema.
     * 
     * @param perfil el perfil a guardar (sin ID)
     * @return el perfil guardado con su ID asignado
     * @throws IllegalArgumentException                            si el perfil es
     *                                                             nulo
     * @throws com.guma.domain.exceptions.PerfilDuplicadoException si el usuario ya
     *                                                             tiene perfil
     * @throws com.guma.domain.exceptions.DniDuplicadoException    si el DNI ya
     *                                                             existe
     */
    PerfilUsuario save(PerfilUsuario perfil);

    /**
     * Guarda un nuevo perfil usando una conexión transaccional existente.
     * Versión para uso dentro de transacciones (Unit of Work).
     * 
     * @param perfil el perfil a guardar (sin ID)
     * @param conn   la conexión transaccional a usar
     * @return el perfil guardado con su ID asignado
     * @throws SQLException                                        si ocurre un
     *                                                             error
     *                                                             de base de datos
     * @throws IllegalArgumentException                            si el perfil es
     *                                                             nulo
     * @throws com.guma.domain.exceptions.PerfilDuplicadoException si el usuario ya
     *                                                             tiene perfil
     * @throws com.guma.domain.exceptions.DniDuplicadoException    si el DNI ya
     *                                                             existe
     */
    PerfilUsuario save(PerfilUsuario perfil, Connection conn) throws SQLException;

    /**
     * Actualiza un perfil de usuario existente.
     * 
     * @param perfil el perfil a actualizar (debe tener ID)
     * @return el perfil actualizado
     * @throws IllegalArgumentException                                si el perfil
     *                                                                 es nulo o no
     *                                                                 tiene ID
     * @throws com.guma.domain.exceptions.EntidadNoEncontradaException si el perfil
     *                                                                 no existe
     * @throws com.guma.domain.exceptions.DniDuplicadoException        si el DNI ya
     *                                                                 existe en
     *                                                                 otro perfil
     */
    PerfilUsuario update(PerfilUsuario perfil);

    /**
     * Actualiza un perfil usando una conexión transaccional existente.
     * Versión para uso dentro de transacciones (Unit of Work).
     * 
     * @param perfil el perfil a actualizar (debe tener ID)
     * @param conn   la conexión transaccional a usar
     * @return el perfil actualizado
     * @throws SQLException                                            si ocurre un
     *                                                                 error de base
     *                                                                 de
     *                                                                 datos
     * @throws IllegalArgumentException                                si el perfil
     *                                                                 es nulo o no
     *                                                                 tiene ID
     * @throws com.guma.domain.exceptions.EntidadNoEncontradaException si el perfil
     *                                                                 no existe
     * @throws com.guma.domain.exceptions.DniDuplicadoException        si el DNI ya
     *                                                                 existe en
     *                                                                 otro perfil
     */
    PerfilUsuario update(PerfilUsuario perfil, Connection conn) throws SQLException;

    /**
     * Verifica si existe un perfil para el usuario especificado.
     * 
     * @param idUsuario el ID del usuario
     * @return true si el usuario tiene perfil, false en caso contrario
     */
    boolean existsByUsuarioId(Integer idUsuario);

    /**
     * Verifica si existe un perfil con el DNI especificado.
     * 
     * @param dni el DNI a verificar
     * @return true si existe un perfil con ese DNI, false en caso contrario
     */
    boolean existsByDni(String dni);
}
