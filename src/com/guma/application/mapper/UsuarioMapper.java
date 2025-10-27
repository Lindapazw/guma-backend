package com.guma.application.mapper;

import com.guma.domain.entities.*;
import com.guma.application.dto.*;

/**
 * Mapper para convertir entre Usuario (entidad) y UsuarioDTO.
 * Responsable de transformar objetos de dominio a DTOs y viceversa.
 * 
 * Conversiones:
 * - toDTO: Usuario → UsuarioDTO (para enviar al frontend)
 * - toEntity: UsuarioDTO → Usuario (si fuera necesario reconstruir)
 * 
 * @author GUMA Development Team
 * @version 1.1 - Actualizado para incluir perfil
 */
public class UsuarioMapper {

    /**
     * Convierte una entidad Usuario a UsuarioDTO.
     * Incluye información del rol y perfil asociados.
     * 
     * @param usuario Entidad de dominio
     * @param rol     Rol asociado al usuario
     * @param perfil  Perfil del usuario (puede ser null)
     * @return UsuarioDTO con los datos del usuario
     * @throws IllegalArgumentException si usuario es null
     */
    public static UsuarioDTO toDTO(Usuario usuario, Rol rol, PerfilUsuario perfil) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser null");
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setEmail(usuario.getEmail().getValor());
        dto.setIdRol(usuario.getIdRol());
        dto.setVerificado(usuario.estaVerificado());
        dto.setUltimaConexion(usuario.getUltimaConexion());

        // Agregar información del rol si está disponible
        if (rol != null) {
            dto.setNombreRol(rol.getNombre());
        }

        // Agregar información del perfil si está disponible
        if (perfil != null) {
            dto.setNombre(perfil.getNombre());
            dto.setApellido(perfil.getApellido());
            dto.setTelefono(perfil.getEmail()); // Temporal: usando email como teléfono
            dto.setEstado("activo"); // Estado por defecto
        }

        return dto;
    }

    /**
     * Convierte una entidad Usuario a UsuarioDTO sin información de rol ni perfil.
     * 
     * @param usuario Entidad de dominio
     * @return UsuarioDTO con los datos del usuario
     */
    public static UsuarioDTO toDTO(Usuario usuario) {
        return toDTO(usuario, null, null);
    }

    /**
     * Convierte una entidad Usuario a UsuarioDTO sin información de perfil.
     * 
     * @param usuario Entidad de dominio
     * @param rol     Rol asociado al usuario
     * @return UsuarioDTO con los datos del usuario
     */
    public static UsuarioDTO toDTO(Usuario usuario, Rol rol) {
        return toDTO(usuario, rol, null);
    }
}
