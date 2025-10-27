package com.guma.application.facade;

import com.guma.application.dto.PerfilUsuarioDTO;
import com.guma.application.dto.ResultadoDTO;

/**
 * Facade (interfaz) para operaciones de perfil de usuario.
 * Define el contrato para las operaciones relacionadas con perfiles.
 * 
 * Operaciones:
 * - crear: Crear un nuevo perfil
 * - actualizar: Actualizar datos de perfil existente
 * - obtenerPorUsuario: Consultar perfil por ID de usuario
 * - obtenerPorDni: Consultar perfil por DNI
 * - verificar: Marcar perfil como verificado
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface PerfilFacade {

    /**
     * Crea un nuevo perfil de usuario.
     * 
     * Proceso:
     * 1. Validar datos de entrada
     * 2. Verificar que el usuario exista
     * 3. Verificar que no tenga perfil ya
     * 4. Verificar que el DNI no esté registrado
     * 5. Crear perfil
     * 
     * @param perfil Datos del perfil a crear
     * @return ResultadoDTO con PerfilUsuarioDTO creado, o errores
     */
    ResultadoDTO<PerfilUsuarioDTO> crear(PerfilUsuarioDTO perfil);

    /**
     * Actualiza un perfil existente.
     * 
     * Proceso:
     * 1. Validar datos de entrada
     * 2. Verificar que el perfil exista
     * 3. Validar cambios (DNI único si cambió)
     * 4. Actualizar perfil
     * 
     * @param perfil Datos actualizados del perfil
     * @return ResultadoDTO con PerfilUsuarioDTO actualizado, o errores
     */
    ResultadoDTO<PerfilUsuarioDTO> actualizar(PerfilUsuarioDTO perfil);

    /**
     * Actualiza un perfil existente con foto opcional.
     * 
     * Proceso:
     * 1. Validar datos de entrada
     * 2. Verificar que el perfil exista
     * 3. Validar cambios (DNI único si cambió)
     * 4. Guardar foto si se proporciona
     * 5. Actualizar perfil en transacción
     * 
     * @param perfil     Datos actualizados del perfil
     * @param fotoBytes  Bytes de la nueva foto (null si no se cambia)
     * @param nombreFoto Nombre del archivo de foto
     * @return ResultadoDTO con PerfilUsuarioDTO actualizado, o errores
     */
    ResultadoDTO<PerfilUsuarioDTO> actualizar(PerfilUsuarioDTO perfil, byte[] fotoBytes, String nombreFoto);

    /**
     * Obtiene el perfil de un usuario por su ID de usuario.
     * 
     * @param idUsuario ID del usuario
     * @return ResultadoDTO con PerfilUsuarioDTO si existe, o error
     */
    ResultadoDTO<PerfilUsuarioDTO> obtenerPorUsuario(Integer idUsuario);

    /**
     * Obtiene un perfil por DNI.
     * 
     * @param dni DNI a buscar
     * @return ResultadoDTO con PerfilUsuarioDTO si existe, o error
     */
    ResultadoDTO<PerfilUsuarioDTO> obtenerPorDni(String dni);

    /**
     * Verifica un perfil de usuario.
     * 
     * @param idPerfilUsuario ID del perfil a verificar
     * @return ResultadoDTO con PerfilUsuarioDTO verificado, o error
     */
    ResultadoDTO<PerfilUsuarioDTO> verificar(Integer idPerfilUsuario);
}
