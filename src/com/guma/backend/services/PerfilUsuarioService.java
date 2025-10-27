package com.guma.backend.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import com.guma.backend.ports.FileStorage;
import com.guma.backend.ports.ImageRepository;
import com.guma.backend.ports.PerfilUsuarioRepository;
import com.guma.backend.ports.UsuarioRepository;
import com.guma.domain.entities.Image;
import com.guma.domain.entities.PerfilUsuario;
import com.guma.domain.exceptions.DniDuplicadoException;
import com.guma.domain.exceptions.EntidadNoEncontradaException;
import com.guma.domain.exceptions.PerfilDuplicadoException;

/**
 * Servicio que gestiona la lógica de negocio relacionada con perfiles de
 * usuario.
 * 
 * Coordina las operaciones de creación y actualización de la información
 * personal de los usuarios, validando las reglas de negocio del dominio.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilUsuarioService {

    private final PerfilUsuarioRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final FileStorage fileStorage;
    private final ImageRepository imageRepository;

    /**
     * Constructor que inyecta las dependencias necesarias.
     * 
     * @param perfilRepository  repositorio de perfiles
     * @param usuarioRepository repositorio de usuarios
     * @param fileStorage       servicio de almacenamiento de archivos
     * @param imageRepository   repositorio de imágenes
     */
    public PerfilUsuarioService(PerfilUsuarioRepository perfilRepository,
            UsuarioRepository usuarioRepository, FileStorage fileStorage,
            ImageRepository imageRepository) {
        if (perfilRepository == null || usuarioRepository == null ||
                fileStorage == null || imageRepository == null) {
            throw new IllegalArgumentException("Los repositorios y servicios no pueden ser nulos");
        }
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
        this.fileStorage = fileStorage;
        this.imageRepository = imageRepository;
    }

    /**
     * Crea un nuevo perfil para un usuario.
     * 
     * Validaciones:
     * - El usuario debe existir
     * - El usuario no puede tener ya un perfil
     * - El DNI debe ser único
     * 
     * @param idUsuario       ID del usuario
     * @param idSexo          ID del sexo (FK a SEXOS)
     * @param dni             documento nacional de identidad
     * @param nombre          nombre del usuario
     * @param apellido        apellido del usuario
     * @param fechaNacimiento fecha de nacimiento
     * @param email           email de contacto
     * @return el perfil creado
     * @throws EntidadNoEncontradaException si el usuario no existe
     * @throws PerfilDuplicadoException     si el usuario ya tiene perfil
     * @throws DniDuplicadoException        si el DNI ya existe
     */
    public PerfilUsuario crearPerfil(
            Integer idUsuario, Integer idSexo, String dni, String nombre, String apellido,
            LocalDate fechaNacimiento, String email) {

        // Validar que el usuario exista
        usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadNoEncontradaException("Usuario", idUsuario));

        // Validar que el usuario no tenga ya un perfil
        if (perfilRepository.existsByUsuarioId(idUsuario)) {
            throw new PerfilDuplicadoException(idUsuario);
        }

        // Validar que el DNI no exista (solo si el DNI no es null)
        // NULL significa "sin DNI asignado" y puede haber múltiples usuarios sin DNI
        if (dni != null && perfilRepository.existsByDni(dni)) {
            throw new DniDuplicadoException(dni);
        }

        // Crear perfil usando constructor simplificado
        PerfilUsuario perfil = new PerfilUsuario(
                idUsuario, idSexo, dni, nombre, apellido, fechaNacimiento, email);

        return perfilRepository.save(perfil);
    }

    /**
     * Crea un nuevo perfil usando una conexión transaccional.
     * Versión para uso dentro de transacciones (Unit of Work).
     * 
     * @param idUsuario       ID del usuario
     * @param idSexo          ID del sexo (FK a SEXOS)
     * @param dni             documento nacional de identidad
     * @param nombre          nombre del usuario
     * @param apellido        apellido del usuario
     * @param fechaNacimiento fecha de nacimiento
     * @param email           email de contacto
     * @param conn            la conexión transaccional a usar
     * @return el perfil creado
     * @throws SQLException                 si ocurre un error de base de datos
     * @throws EntidadNoEncontradaException si el usuario no existe
     * @throws PerfilDuplicadoException     si el usuario ya tiene perfil
     * @throws DniDuplicadoException        si el DNI ya existe
     */
    public PerfilUsuario crearPerfil(
            Integer idUsuario, Integer idSexo, String dni, String nombre, String apellido,
            LocalDate fechaNacimiento, String email, Connection conn) throws SQLException {

        // NOTA: No validamos existencia de usuario porque está dentro de una
        // transacción
        // El usuario recién fue creado en la misma transacción y aún no está committed
        // La validación de FK la hace la base de datos

        // Validar que el usuario no tenga ya un perfil
        // NOTA: Esta validación también se salta porque es el primer perfil que se crea
        // Si ya existiera, la base de datos rechazaría por la constraint UNIQUE en
        // id_usuario

        // Validar que el DNI no exista (solo si el DNI no es null)
        // NOTA: No podemos validar en memoria porque estamos en transacción
        // La base de datos validará la constraint UNIQUE del DNI

        // Crear perfil usando constructor simplificado
        PerfilUsuario perfil = new PerfilUsuario(
                idUsuario, idSexo, dni, nombre, apellido, fechaNacimiento, email);

        return perfilRepository.save(perfil, conn);
    }

    /**
     * Actualiza un perfil existente.
     * 
     * @param perfil el perfil con los datos actualizados
     * @return el perfil actualizado
     * @throws EntidadNoEncontradaException si el perfil no existe
     * @throws DniDuplicadoException        si el DNI ya existe en otro perfil
     */
    public PerfilUsuario actualizarPerfil(PerfilUsuario perfil) {
        if (perfil == null || perfil.getIdPerfilUsuario() == null) {
            throw new IllegalArgumentException("El perfil debe tener un ID");
        }

        // Verificar que el perfil exista
        perfilRepository.findById(perfil.getIdPerfilUsuario())
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "PerfilUsuario", perfil.getIdPerfilUsuario()));

        // Si cambió el DNI, validar que no exista (solo si el DNI no es null)
        // NULL significa "sin DNI asignado" y puede haber múltiples usuarios sin DNI
        if (perfil.getDni() != null) {
            Optional<PerfilUsuario> perfilConDni = perfilRepository.findByDni(perfil.getDni());
            if (perfilConDni.isPresent() &&
                    !perfilConDni.get().getIdPerfilUsuario().equals(perfil.getIdPerfilUsuario())) {
                throw new DniDuplicadoException(perfil.getDni());
            }
        }

        return perfilRepository.update(perfil);
    }

    /**
     * Actualiza un perfil existente con foto opcional.
     * 
     * @param perfil     el perfil con los datos actualizados
     * @param fotoBytes  bytes de la nueva foto (null si no se cambia)
     * @param nombreFoto nombre del archivo de foto
     * @param conn       conexión transaccional
     * @return el perfil actualizado
     * @throws EntidadNoEncontradaException si el perfil no existe
     * @throws DniDuplicadoException        si el DNI ya existe en otro perfil
     * @throws IOException                  si falla el guardado de la foto
     */
    public PerfilUsuario actualizarPerfil(PerfilUsuario perfil, byte[] fotoBytes,
            String nombreFoto, Connection conn) throws SQLException, IOException {

        if (perfil == null || perfil.getIdPerfilUsuario() == null) {
            throw new IllegalArgumentException("El perfil debe tener un ID");
        }

        // Verificar que el perfil exista
        PerfilUsuario perfilExistente = perfilRepository.findById(perfil.getIdPerfilUsuario())
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "PerfilUsuario", perfil.getIdPerfilUsuario()));

        // Si cambió el DNI, validar que no exista
        if (perfil.getDni() != null) {
            Optional<PerfilUsuario> perfilConDni = perfilRepository.findByDni(perfil.getDni());
            if (perfilConDni.isPresent() &&
                    !perfilConDni.get().getIdPerfilUsuario().equals(perfil.getIdPerfilUsuario())) {
                throw new DniDuplicadoException(perfil.getDni());
            }
        }

        // Si hay nueva foto, guardarla
        if (fotoBytes != null && fotoBytes.length > 0 && nombreFoto != null) {
            try {
                // 1. Guardar archivo físico con FileStorage
                String rutaFoto = fileStorage.guardarArchivo(
                        "usuario_perfil",
                        perfilExistente.getIdUsuario(),
                        nombreFoto,
                        fotoBytes);

                // 2. Crear nuevo registro de imagen EN LA MISMA TRANSACCIÓN
                Image nuevaImagen = new Image(rutaFoto);
                Image imagenGuardada = imageRepository.save(nuevaImagen, conn);

                // 3. Guardar ID de imagen anterior para eliminar después
                Integer imagenAnteriorId = perfilExistente.getFotoPerfil();

                // 4. Asignar nuevo ID de imagen al perfil
                perfil.setFotoPerfil(imagenGuardada.getIdImage());

                // 5. Actualizar perfil PRIMERO (antes de eliminar imagen vieja)
                PerfilUsuario perfilActualizado = perfilRepository.update(perfil, conn);

                // 6. AHORA eliminar imagen anterior si existía (ya no hay FK constraint)
                if (imagenAnteriorId != null) {
                    Optional<Image> imagenAnterior = imageRepository.findById(imagenAnteriorId);
                    if (imagenAnterior.isPresent()) {
                        // Eliminar archivo físico
                        fileStorage.eliminarArchivo(imagenAnterior.get().getLink());
                        // Eliminar registro de BD EN LA MISMA TRANSACCIÓN
                        imageRepository.delete(imagenAnteriorId, conn);
                    }
                }

                return perfilActualizado;

            } catch (IOException e) {
                throw new IOException("Error al guardar foto de perfil: " + e.getMessage(), e);
            }
        } else {
            // Mantener foto existente si no se proporciona una nueva
            perfil.setFotoPerfil(perfilExistente.getFotoPerfil());
        }

        return perfilRepository.update(perfil, conn);
    }

    /**
     * Verifica un perfil de usuario.
     * 
     * @param idPerfilUsuario ID del perfil a verificar
     * @throws EntidadNoEncontradaException si el perfil no existe
     */
    public void verificarPerfil(Integer idPerfilUsuario) {
        PerfilUsuario perfil = perfilRepository.findById(idPerfilUsuario)
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "PerfilUsuario", idPerfilUsuario));

        perfil.verificar();
        perfilRepository.update(perfil);
    }

    /**
     * Busca el perfil de un usuario por su ID de usuario.
     * 
     * @param idUsuario ID del usuario
     * @return Optional con el perfil si existe
     */
    public Optional<PerfilUsuario> buscarPorUsuarioId(Integer idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo");
        }
        return perfilRepository.findByUsuarioId(idUsuario);
    }

    /**
     * Busca un perfil por su DNI.
     * 
     * @param dni el DNI a buscar
     * @return Optional con el perfil si existe
     */
    public Optional<PerfilUsuario> buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío");
        }
        return perfilRepository.findByDni(dni);
    }

    /**
     * Verifica si un perfil está completo (tiene todos los datos obligatorios).
     * 
     * @param idPerfilUsuario ID del perfil
     * @return true si el perfil está completo
     * @throws EntidadNoEncontradaException si el perfil no existe
     */
    public boolean esPerfilCompleto(Integer idPerfilUsuario) {
        PerfilUsuario perfil = perfilRepository.findById(idPerfilUsuario)
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "PerfilUsuario", idPerfilUsuario));
        return perfil.estaCompleto();
    }
}
