package com.guma.backend.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.guma.backend.ports.RolRepository;
import com.guma.backend.ports.UsuarioRepository;
import com.guma.domain.entities.Rol;
import com.guma.domain.entities.Usuario;
import com.guma.domain.exceptions.EmailInvalidoException;
import com.guma.domain.exceptions.EntidadNoEncontradaException;
import com.guma.domain.exceptions.PasswordDebilException;
import com.guma.domain.exceptions.UsuarioDuplicadoException;
import com.guma.domain.valueobjects.Email;
import com.guma.domain.valueobjects.Password;

/**
 * Servicio que gestiona la lógica de negocio relacionada con usuarios.
 * 
 * Coordina las operaciones entre los repositorios y aplica las reglas de
 * negocio
 * del dominio para el registro, autenticación y gestión de usuarios.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    /**
     * Constructor que inyecta las dependencias necesarias.
     * 
     * @param usuarioRepository repositorio de usuarios
     * @param rolRepository     repositorio de roles
     */
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        if (usuarioRepository == null || rolRepository == null) {
            throw new IllegalArgumentException("Los repositorios no pueden ser nulos");
        }
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    /**
     * Registra un nuevo usuario en el sistema con el rol por defecto.
     * 
     * Validaciones realizadas:
     * - Email único en el sistema
     * - Password cumple requisitos de seguridad (delegado a Password value object)
     * - Asignación automática del rol por defecto (ID 3 - Usuario)
     * 
     * @param emailStr    el email del nuevo usuario
     * @param passwordStr la contraseña sin encriptar
     * @return el usuario registrado con su ID asignado
     * @throws UsuarioDuplicadoException    si el email ya está registrado
     * @throws EmailInvalidoException       si el formato del email es inválido
     * @throws PasswordDebilException       si la contraseña no cumple los
     *                                      requisitos
     * @throws EntidadNoEncontradaException si no existe el rol por defecto
     */
    public Usuario registrarUsuario(String emailStr, String passwordStr) {
        // Validar que el email no exista
        Email email = Email.crear(emailStr);
        if (usuarioRepository.existsByEmail(email)) {
            throw new UsuarioDuplicadoException(emailStr);
        }

        // Crear password encriptada (validaciones internas)
        Password password = Password.crear(passwordStr);

        // Obtener rol por defecto
        Rol rolPorDefecto = rolRepository.getRolPorDefecto();

        // Crear y guardar usuario
        Usuario nuevoUsuario = new Usuario(
                null, // ID será asignado por la BD
                email,
                password,
                rolPorDefecto.getIdRol(),
                true, // Verificado por defecto (sin verificación de email por ahora)
                null // ultimaConexion será null hasta que inicie sesión
        );

        return usuarioRepository.save(nuevoUsuario);
    }

    /**
     * Registra un nuevo usuario usando una conexión transaccional.
     * Versión para uso dentro de transacciones (Unit of Work).
     * 
     * @param emailStr    el email del nuevo usuario
     * @param passwordStr la contraseña sin encriptar
     * @param conn        la conexión transaccional a usar
     * @return el usuario registrado con su ID asignado
     * @throws SQLException                 si ocurre un error de base de datos
     * @throws UsuarioDuplicadoException    si el email ya está registrado
     * @throws EmailInvalidoException       si el formato del email es inválido
     * @throws PasswordDebilException       si la contraseña no cumple los
     *                                      requisitos
     * @throws EntidadNoEncontradaException si no existe el rol por defecto
     */
    public Usuario registrarUsuario(String emailStr, String passwordStr, Connection conn) throws SQLException {
        // Validar que el email no exista
        Email email = Email.crear(emailStr);
        if (usuarioRepository.existsByEmail(email)) {
            throw new UsuarioDuplicadoException(emailStr);
        }

        // Crear password encriptada (validaciones internas)
        Password password = Password.crear(passwordStr);

        // Obtener rol por defecto
        Rol rolPorDefecto = rolRepository.getRolPorDefecto();

        // Crear y guardar usuario con conexión transaccional
        Usuario nuevoUsuario = new Usuario(
                null,
                email,
                password,
                rolPorDefecto.getIdRol(),
                true, // Verificado por defecto (sin verificación de email por ahora)
                null);

        return usuarioRepository.save(nuevoUsuario, conn);
    }

    /**
     * Valida si un email está disponible para registro.
     * 
     * @param emailStr el email a validar
     * @return true si el email está disponible, false si ya existe
     * @throws EmailInvalidoException si el formato del email es inválido
     */
    public boolean validarEmailDisponible(String emailStr) {
        Email email = Email.crear(emailStr);
        return !usuarioRepository.existsByEmail(email);
    }

    /**
     * Valida si una contraseña cumple los requisitos de seguridad.
     * 
     * @param passwordStr la contraseña a validar
     * @return true si la contraseña es válida
     * @throws PasswordDebilException si la contraseña no cumple los requisitos
     */
    public boolean validarPassword(String passwordStr) {
        try {
            Password.crear(passwordStr);
            return true;
        } catch (PasswordDebilException e) {
            throw e; // Re-lanzar la excepción para que el cliente maneje el error
        }
    }

    /**
     * Autentica un usuario con email y contraseña.
     * 
     * @param emailStr    el email del usuario
     * @param passwordStr la contraseña sin encriptar
     * @return el usuario autenticado con última conexión actualizada
     * @throws EntidadNoEncontradaException si el usuario no existe o la contraseña
     *                                      es incorrecta
     * @throws EmailInvalidoException       si el formato del email es inválido
     */
    public Usuario iniciarSesion(String emailStr, String passwordStr) {
        Email email = Email.crear(emailStr);

        // Buscar usuario por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new EntidadNoEncontradaException("Usuario con email: " + emailStr);
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar contraseña
        if (!usuario.getPassword().verificar(passwordStr)) {
            throw new EntidadNoEncontradaException("Credenciales inválidas");
        }

        // Actualizar última conexión
        usuario.actualizarUltimaConexion();

        // Guardar cambios
        return usuarioRepository.update(usuario);
    }

    /**
     * Busca un usuario por su email.
     * 
     * @param emailStr el email a buscar
     * @return Optional con el usuario si existe
     * @throws EmailInvalidoException si el formato del email es inválido
     */
    public Optional<Usuario> buscarPorEmail(String emailStr) {
        Email email = Email.crear(emailStr);
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param idUsuario el ID del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> buscarPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    /**
     * Verifica el email de un usuario.
     * 
     * @param idUsuario el ID del usuario a verificar
     * @throws EntidadNoEncontradaException si el usuario no existe
     */
    public void verificarEmail(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadNoEncontradaException("Usuario", idUsuario));

        usuario.verificarEmail();
        usuarioRepository.update(usuario);
    }
}
