package com.guma.application.facade.impl;

import java.util.List;
import java.util.Optional;

import com.guma.application.dto.ErrorDTO;
import com.guma.application.dto.PerfilUsuarioDTO;
import com.guma.application.dto.ResultadoDTO;
import com.guma.application.facade.PerfilFacade;
import com.guma.application.factory.ServiceFactory;
import com.guma.application.mapper.PerfilUsuarioMapper;
import com.guma.application.validator.PerfilUsuarioValidator;
import com.guma.backend.ports.ImageRepository;
import com.guma.backend.ports.PerfilUsuarioRepository;
import com.guma.backend.services.PerfilUsuarioService;
import com.guma.backend.services.UsuarioService;
import com.guma.data.transaction.JdbcUnitOfWork;
import com.guma.domain.entities.Image;
import com.guma.domain.entities.PerfilUsuario;
import com.guma.domain.entities.Usuario;
import com.guma.domain.exceptions.DniDuplicadoException;
import com.guma.domain.exceptions.EntidadNoEncontradaException;
import com.guma.domain.exceptions.PerfilDuplicadoException;
import com.guma.domain.transaction.UnitOfWork;

/**
 * Implementación del facade de perfil de usuario.
 * Coordina las operaciones entre validadores, servicios y mappers.
 * 
 * Responsabilidades:
 * - Validar datos de entrada (DTOs)
 * - Llamar servicios de backend
 * - Convertir entidades a DTOs y viceversa
 * - Manejar excepciones y convertirlas a ResultadoDTO
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilFacadeImpl implements PerfilFacade {

    private final PerfilUsuarioService perfilService;
    private final UsuarioService usuarioService;
    private final ImageRepository imageRepository;
    private final UnitOfWork unitOfWork;

    /**
     * Constructor que inicializa el servicio necesario.
     */
    public PerfilFacadeImpl() {
        this.perfilService = ServiceFactory.crearPerfilUsuarioService();
        this.usuarioService = ServiceFactory.crearUsuarioService();
        this.imageRepository = ServiceFactory.getImageRepository();
        this.unitOfWork = new JdbcUnitOfWork();
    }

    /**
     * Constructor para testing (permite inyectar servicio mock).
     */
    public PerfilFacadeImpl(PerfilUsuarioService perfilService, UsuarioService usuarioService,
            ImageRepository imageRepository, UnitOfWork unitOfWork) {
        this.perfilService = perfilService;
        this.usuarioService = usuarioService;
        this.imageRepository = imageRepository;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public ResultadoDTO<PerfilUsuarioDTO> crear(PerfilUsuarioDTO perfilDTO) {
        try {
            // 1. Validar datos de entrada
            List<ErrorDTO> errores = PerfilUsuarioValidator.validar(perfilDTO);
            if (!errores.isEmpty()) {
                return ResultadoDTO.error(errores);
            }

            // 2. Crear perfil (el servicio espera parámetros individuales)
            PerfilUsuario perfilCreado = perfilService.crearPerfil(
                    perfilDTO.getIdUsuario(),
                    perfilDTO.getIdSexo(),
                    perfilDTO.getDni(),
                    perfilDTO.getNombre(),
                    perfilDTO.getApellido(),
                    perfilDTO.getFechaNacimiento(),
                    perfilDTO.getEmail());

            // 3. Convertir a DTO y retornar
            PerfilUsuarioDTO dto = PerfilUsuarioMapper.toDTO(perfilCreado);
            return ResultadoDTO.exito(dto);

        } catch (PerfilDuplicadoException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (DniDuplicadoException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Usuario no encontrado");
        } catch (IllegalArgumentException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResultadoDTO.error("Error al crear perfil: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<PerfilUsuarioDTO> actualizar(PerfilUsuarioDTO perfilDTO) {
        try {
            // 1. Validar que tenga ID
            if (perfilDTO.getIdPerfilUsuario() == null) {
                return ResultadoDTO.error("El ID del perfil es obligatorio para actualizar");
            }

            // 2. Validar datos de entrada
            List<ErrorDTO> errores = PerfilUsuarioValidator.validar(perfilDTO);
            if (!errores.isEmpty()) {
                return ResultadoDTO.error(errores);
            }

            // 3. Convertir DTO a entidad
            PerfilUsuario perfil = PerfilUsuarioMapper.toEntity(perfilDTO);

            // 4. Actualizar perfil (el servicio valida existencia y unicidad)
            PerfilUsuario perfilActualizado = perfilService.actualizarPerfil(perfil);

            // 5. Convertir a DTO y retornar
            PerfilUsuarioDTO dto = PerfilUsuarioMapper.toDTO(perfilActualizado);
            return ResultadoDTO.exito(dto);

        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Perfil no encontrado");
        } catch (PerfilDuplicadoException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResultadoDTO.error("Error al actualizar perfil: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<PerfilUsuarioDTO> actualizar(PerfilUsuarioDTO perfilDTO,
            byte[] fotoBytes, String nombreFoto) {
        try {
            // 1. Validar que tenga ID
            if (perfilDTO.getIdPerfilUsuario() == null) {
                return ResultadoDTO.error("El ID del perfil es obligatorio para actualizar");
            }

            // 2. Validar datos de entrada
            List<ErrorDTO> errores = PerfilUsuarioValidator.validar(perfilDTO);
            if (!errores.isEmpty()) {
                return ResultadoDTO.error(errores);
            }

            // 3. Convertir DTO a entidad
            PerfilUsuario perfil = PerfilUsuarioMapper.toEntity(perfilDTO);

            // 4. Ejecutar actualización en transacción atómica
            PerfilUsuario perfilActualizado = unitOfWork.execute(conn -> {
                return perfilService.actualizarPerfil(perfil, fotoBytes, nombreFoto, conn);
            });

            // 5. Convertir a DTO y retornar
            PerfilUsuarioDTO dto = PerfilUsuarioMapper.toDTO(perfilActualizado);
            return ResultadoDTO.exito(dto);

        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Perfil no encontrado");
        } catch (DniDuplicadoException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (Exception e) {
            // IOException y SQLException se envuelven aquí
            return ResultadoDTO.error("Error al actualizar perfil: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<PerfilUsuarioDTO> obtenerPorUsuario(Integer idUsuario) {
        try {
            // 1. Validar ID
            if (idUsuario == null) {
                return ResultadoDTO.error("El ID de usuario es obligatorio");
            }

            // 2. Buscar perfil
            Optional<PerfilUsuario> perfilOpt = perfilService.buscarPorUsuarioId(idUsuario);
            if (perfilOpt.isEmpty()) {
                return ResultadoDTO.error("Perfil no encontrado para el usuario");
            }

            PerfilUsuario perfil = perfilOpt.get();

            // 3. Buscar usuario para obtener ultima conexión
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(idUsuario);

            // 4. Convertir a DTO
            PerfilUsuarioDTO dto = PerfilUsuarioMapper.toDTO(perfil);

            // 5. Agregar ultimaConexion desde Usuario
            if (usuarioOpt.isPresent()) {
                dto.setUltimaConexion(usuarioOpt.get().getUltimaConexion());
            }

            // 6. Agregar URL de foto si existe
            if (perfil.getFotoPerfil() != null) {
                Optional<Image> imagenOpt = imageRepository.findById(perfil.getFotoPerfil());
                if (imagenOpt.isPresent()) {
                    // El link en la BD es relativo (ej: "usuarios/1/foto.jpg")
                    // Lo devolvemos tal cual, el frontend se encarga de construir la ruta completa
                    dto.setFotoPerfilUrl(imagenOpt.get().getLink());
                }
            }

            return ResultadoDTO.exito(dto);

        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener perfil: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<PerfilUsuarioDTO> obtenerPorDni(String dni) {
        try {
            // 1. Validar DNI
            if (dni == null || dni.trim().isEmpty()) {
                return ResultadoDTO.error("El DNI es obligatorio");
            }

            // 2. Buscar perfil
            Optional<PerfilUsuario> perfilOpt = perfilService.buscarPorDni(dni);
            if (perfilOpt.isEmpty()) {
                return ResultadoDTO.error("Perfil no encontrado para el DNI");
            }

            // 3. Convertir y retornar
            PerfilUsuarioDTO dto = PerfilUsuarioMapper.toDTO(perfilOpt.get());
            return ResultadoDTO.exito(dto);

        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener perfil: " + e.getMessage());
        }
    }

    @Override
    public ResultadoDTO<PerfilUsuarioDTO> verificar(Integer idPerfilUsuario) {
        try {
            // 1. Validar ID
            if (idPerfilUsuario == null) {
                return ResultadoDTO.error("El ID del perfil es obligatorio");
            }

            // 2. Verificar perfil (retorna void)
            perfilService.verificarPerfil(idPerfilUsuario);

            // 3. Obtener perfil actualizado directamente del repositorio
            PerfilUsuarioRepository perfilRepository = ServiceFactory.getPerfilUsuarioRepository();
            Optional<PerfilUsuario> perfilOpt = perfilRepository.findById(idPerfilUsuario);
            if (perfilOpt.isEmpty()) {
                return ResultadoDTO.error("Perfil no encontrado");
            }
            PerfilUsuario perfil = perfilOpt.get();

            // 4. Convertir y retornar
            PerfilUsuarioDTO dto = PerfilUsuarioMapper.toDTO(perfil);
            return ResultadoDTO.exito(dto);

        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Perfil no encontrado");
        } catch (Exception e) {
            return ResultadoDTO.error("Error al verificar perfil: " + e.getMessage());
        }
    }
}
