package com.guma.application.facade;

import com.guma.application.dto.LoginDTO;
import com.guma.application.dto.RegistroUsuarioDTO;
import com.guma.application.dto.ResultadoDTO;
import com.guma.application.dto.SesionDTO;
import com.guma.application.dto.UsuarioDTO;

/**
 * Facade (interfaz) para operaciones de autenticación.
 * Define el contrato para las operaciones relacionadas con usuarios.
 * 
 * Operaciones:
 * - registrarUsuario: Crear un nuevo usuario con datos completos
 * - iniciarSesion: Autenticar usuario y retornar sesión
 * - verificarEmail: Marcar email como verificado
 * - obtenerUsuario: Consultar datos de usuario
 * 
 * @author GUMA Development Team
 * @version 1.1 - Actualizado para coincidir con frontend
 */
public interface AuthFacade {

    /**
     * Registra un nuevo usuario en el sistema con datos completos.
     * 
     * Proceso:
     * 1. Validar datos de entrada (email, password, nombre, apellido)
     * 2. Verificar que el email no esté registrado
     * 3. Crear usuario con rol por defecto
     * 4. Crear perfil del usuario con nombre y apellido
     * 5. Retornar DTO con datos del usuario creado
     * 
     * @param registro Datos de registro del usuario (email, password, nombre,
     *                 apellido, telefono)
     * @return ResultadoDTO con UsuarioDTO si fue exitoso, o errores
     */
    ResultadoDTO<UsuarioDTO> registrarUsuario(RegistroUsuarioDTO registro);

    /**
     * Inicia sesión de un usuario.
     * 
     * Proceso:
     * 1. Buscar usuario por email
     * 2. Verificar contraseña
     * 3. Actualizar última conexión
     * 4. Retornar SesionDTO con todos los datos necesarios para la UI
     * 
     * @param loginDTO Credenciales del usuario (email y password)
     * @return ResultadoDTO con SesionDTO si fue exitoso, o errores
     */
    ResultadoDTO<SesionDTO> iniciarSesion(LoginDTO loginDTO);

    /**
     * Verifica el email de un usuario.
     * 
     * @param idUsuario ID del usuario a verificar
     * @return ResultadoDTO con UsuarioDTO actualizado, o errores
     */
    ResultadoDTO<UsuarioDTO> verificarEmail(Integer idUsuario);

    /**
     * Obtiene los datos de un usuario por ID.
     * 
     * @param idUsuario ID del usuario
     * @return ResultadoDTO con UsuarioDTO si existe, o error
     */
    ResultadoDTO<UsuarioDTO> obtenerUsuario(Integer idUsuario);

    /**
     * Obtiene los datos de un usuario por email.
     * 
     * @param email Email del usuario
     * @return ResultadoDTO con UsuarioDTO si existe, o error
     */
    ResultadoDTO<UsuarioDTO> obtenerUsuarioPorEmail(String email);
}
