package com.guma.application.facade.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.guma.application.dto.ErrorDTO;
import com.guma.application.dto.LoginDTO;
import com.guma.application.dto.RegistroUsuarioDTO;
import com.guma.application.dto.ResultadoDTO;
import com.guma.application.dto.SesionDTO;
import com.guma.application.dto.UsuarioDTO;
import com.guma.application.facade.AuthFacade;
import com.guma.application.factory.ServiceFactory;
import com.guma.application.mapper.UsuarioMapper;
import com.guma.application.validator.RegistroUsuarioValidator;
import com.guma.backend.services.PerfilUsuarioService;
import com.guma.backend.services.RolService;
import com.guma.backend.services.UsuarioService;
import com.guma.data.transaction.JdbcUnitOfWork;
import com.guma.domain.entities.PerfilUsuario;
import com.guma.domain.entities.Rol;
import com.guma.domain.entities.Usuario;
import com.guma.domain.exceptions.EntidadNoEncontradaException;
import com.guma.domain.exceptions.UsuarioDuplicadoException;
import com.guma.domain.transaction.UnitOfWork;

/**
 * Implementación del facade de autenticación.
 * Coordina las operaciones entre validadores, servicios y mappers.
 * 
 * Responsabilidades:
 * - Validar datos de entrada (DTOs)
 * - Llamar servicios de backend
 * - Convertir entidades a DTOs
 * - Manejar excepciones y convertirlas a ResultadoDTO
 * - Gestionar transacciones para operaciones multi-tabla
 * 
 * @author GUMA Development Team
 * @version 2.0 - Implementado Unit of Work para transacciones
 */
public class AuthFacadeImpl implements AuthFacade {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final PerfilUsuarioService perfilService;
    private final UnitOfWork unitOfWork;

    /**
     * Constructor que inicializa los servicios necesarios.
     */
    public AuthFacadeImpl() {
        this.usuarioService = ServiceFactory.crearUsuarioService();
        this.rolService = ServiceFactory.crearRolService();
        this.perfilService = ServiceFactory.crearPerfilUsuarioService();
        this.unitOfWork = new JdbcUnitOfWork();
    }

    /**
     * Constructor para testing (permite inyectar servicios mock).
     */
    public AuthFacadeImpl(UsuarioService usuarioService, RolService rolService, PerfilUsuarioService perfilService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
        this.perfilService = perfilService;
        this.unitOfWork = new JdbcUnitOfWork();
    }

    @Override
    public ResultadoDTO<UsuarioDTO> registrarUsuario(RegistroUsuarioDTO registro) {
        try {
            // 1. Validar datos de entrada (fuera de transacción)
            List<ErrorDTO> errores = RegistroUsuarioValidator.validar(registro);
            if (!errores.isEmpty()) {
                return ResultadoDTO.error(errores);
            }

            // 2. Preparar fecha de nacimiento
            LocalDate fechaNacimiento = registro.getFechaNacimiento() != null
                    ? registro.getFechaNacimiento()
                    : LocalDate.of(2000, 1, 1);

            // 3. Ejecutar registro en transacción atómica
            Usuario usuario = unitOfWork.execute(conn -> {
                // 3.1. Crear usuario
                Usuario u = usuarioService.registrarUsuario(
                        registro.getEmail(),
                        registro.getPassword(),
                        conn);

                // 3.2. Crear perfil del usuario
                // Si cualquiera de estas operaciones falla, se hace rollback automático
                perfilService.crearPerfil(
                        u.getIdUsuario(),
                        1, // ID sexo por defecto
                        null, // DNI NULL permite múltiples registros sin DNI asignado
                        registro.getNombre(),
                        registro.getApellido(),
                        fechaNacimiento,
                        registro.getEmail(),
                        conn);

                return u;
            });

            // 4. Obtener datos adicionales (fuera de transacción)
            Optional<Rol> rolOpt = rolService.obtenerRolPorId(usuario.getIdRol());
            Rol rol = rolOpt.orElse(null);

            Optional<PerfilUsuario> perfilOpt = perfilService.buscarPorUsuarioId(usuario.getIdUsuario());
            PerfilUsuario perfil = perfilOpt.orElse(null);

            // 5. Convertir a DTO y retornar
            UsuarioDTO dto = UsuarioMapper.toDTO(usuario, rol, perfil);
            return ResultadoDTO.exito(dto);

        } catch (UsuarioDuplicadoException e) {
            return ResultadoDTO.error("El email ya está registrado");
        } catch (IllegalArgumentException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResultadoDTO.error("Error al registrar usuario: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<SesionDTO> iniciarSesion(LoginDTO loginDTO) {
        try {
            // 1. Validar campos del LoginDTO
            if (loginDTO == null) {
                return ResultadoDTO.error("Los datos de login son obligatorios");
            }
            if (loginDTO.getEmail() == null || loginDTO.getEmail().trim().isEmpty()) {
                return ResultadoDTO.error("El email es obligatorio");
            }
            if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
                return ResultadoDTO.error("La contraseña es obligatoria");
            }

            // 2. Iniciar sesión (el servicio valida credenciales)
            Usuario usuario = usuarioService.iniciarSesion(
                    loginDTO.getEmail(),
                    loginDTO.getPassword());

            // 3. Obtener perfil del usuario
            Optional<PerfilUsuario> perfilOpt = perfilService.buscarPorUsuarioId(usuario.getIdUsuario());
            PerfilUsuario perfil = perfilOpt.orElse(null);

            // 4. Obtener rol
            Optional<Rol> rolOpt = rolService.obtenerRolPorId(usuario.getIdRol());
            Rol rol = rolOpt.orElse(null);

            // 5. Construir SesionDTO con toda la información
            SesionDTO sesion = new SesionDTO(
                    usuario.getIdUsuario(),
                    usuario.getEmail().getValor(),
                    perfil != null ? perfil.getNombre() + " " + perfil.getApellido() : usuario.getEmail().getValor(),
                    perfil != null ? perfil.getNombre() : null,
                    perfil != null ? perfil.getApellido() : null,
                    perfil != null ? perfil.getEmail() : null, // teléfono (temporal, usando email)
                    rol != null ? rol.getNombre() : "Usuario",
                    "activo", // estado por defecto
                    usuario.estaVerificado(),
                    null, // token (null por ahora, se implementará JWT después)
                    null, // expiración
                    usuario.getUltimaConexion());

            return ResultadoDTO.exito(sesion);

        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Email o contraseña incorrectos");
        } catch (IllegalArgumentException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResultadoDTO.error("Error al iniciar sesión: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<UsuarioDTO> verificarEmail(Integer idUsuario) {
        try {
            // 1. Validar ID
            if (idUsuario == null) {
                return ResultadoDTO.error("El ID de usuario es obligatorio");
            }

            // 2. Verificar email (retorna void)
            usuarioService.verificarEmail(idUsuario);

            // 3. Obtener usuario actualizado
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(idUsuario);
            if (usuarioOpt.isEmpty()) {
                return ResultadoDTO.error("Usuario no encontrado");
            }
            Usuario usuario = usuarioOpt.get();

            // 4. Obtener rol
            Optional<Rol> rolOpt = rolService.obtenerRolPorId(usuario.getIdRol());
            Rol rol = rolOpt.orElse(null);

            // 5. Convertir y retornar
            UsuarioDTO dto = UsuarioMapper.toDTO(usuario, rol);
            return ResultadoDTO.exito(dto);

        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Usuario no encontrado");
        } catch (Exception e) {
            return ResultadoDTO.error("Error al verificar email: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<UsuarioDTO> obtenerUsuario(Integer idUsuario) {
        try {
            // 1. Validar ID
            if (idUsuario == null) {
                return ResultadoDTO.error("El ID de usuario es obligatorio");
            }

            // 2. Buscar usuario
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(idUsuario);
            if (usuarioOpt.isEmpty()) {
                return ResultadoDTO.error("Usuario no encontrado");
            }

            Usuario usuario = usuarioOpt.get();

            // 3. Obtener rol
            Optional<Rol> rolOpt = rolService.obtenerRolPorId(usuario.getIdRol());
            Rol rol = rolOpt.orElse(null);

            // 4. Convertir y retornar
            UsuarioDTO dto = UsuarioMapper.toDTO(usuario, rol);
            return ResultadoDTO.exito(dto);

        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener usuario: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<UsuarioDTO> obtenerUsuarioPorEmail(String email) {
        try {
            // 1. Validar email
            if (email == null || email.trim().isEmpty()) {
                return ResultadoDTO.error("El email es obligatorio");
            }

            // 2. Buscar usuario (servicio acepta String directamente)
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
            if (usuarioOpt.isEmpty()) {
                return ResultadoDTO.error("Usuario no encontrado");
            }

            Usuario usuario = usuarioOpt.get();

            // 3. Obtener rol
            Optional<Rol> rolOpt = rolService.obtenerRolPorId(usuario.getIdRol());
            Rol rol = rolOpt.orElse(null);

            // 5. Convertir y retornar
            UsuarioDTO dto = UsuarioMapper.toDTO(usuario, rol);
            return ResultadoDTO.exito(dto);

        } catch (IllegalArgumentException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener usuario: " + e.getMessage());
        }
    }
}
