package com.guma.application.mapper;

import com.guma.domain.entities.*;
import com.guma.application.dto.*;

/**
 * Mapper para convertir entre PerfilUsuario (entidad) y PerfilUsuarioDTO.
 * Responsable de transformar objetos de dominio a DTOs y viceversa.
 * 
 * Conversiones:
 * - toDTO: PerfilUsuario → PerfilUsuarioDTO (para enviar al frontend)
 * - toEntity: PerfilUsuarioDTO → PerfilUsuario (reconstrucción desde DTO)
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilUsuarioMapper {

    /**
     * Convierte una entidad PerfilUsuario a PerfilUsuarioDTO.
     * 
     * @param perfil Entidad de dominio
     * @return PerfilUsuarioDTO con los datos del perfil
     * @throws IllegalArgumentException si perfil es null
     */
    public static PerfilUsuarioDTO toDTO(PerfilUsuario perfil) {
        if (perfil == null) {
            throw new IllegalArgumentException("PerfilUsuario no puede ser null");
        }

        PerfilUsuarioDTO dto = new PerfilUsuarioDTO();
        dto.setIdPerfilUsuario(perfil.getIdPerfilUsuario());
        dto.setIdUsuario(perfil.getIdUsuario());
        dto.setIdSexo(perfil.getIdSexo());
        dto.setDni(perfil.getDni());
        dto.setNombre(perfil.getNombre());
        dto.setApellido(perfil.getApellido());
        dto.setFechaNacimiento(perfil.getFechaNacimiento());
        dto.setEmail(perfil.getEmail());
        dto.setTelefono(perfil.getTelefono());
        dto.setIdDireccion(perfil.getIdDireccion());
        dto.setIdRedSocial(perfil.getIdRedSocial());
        dto.setFotoPerfilId(perfil.getFotoPerfil());
        dto.setVerificado(perfil.isVerificado());

        // El nombre del sexo se puede agregar después si se necesita
        // consultando la tabla SEXOS desde el frontend o agregando
        // un servicio de Sexo en el backend

        return dto;
    }

    /**
     * Convierte un PerfilUsuarioDTO a entidad PerfilUsuario.
     * Usado cuando se necesita reconstruir la entidad desde el DTO.
     * 
     * @param dto DTO con datos del perfil
     * @return Entidad PerfilUsuario
     * @throws IllegalArgumentException si dto es null o tiene datos inválidos
     */
    public static PerfilUsuario toEntity(PerfilUsuarioDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("PerfilUsuarioDTO no puede ser null");
        }

        return new PerfilUsuario(
                dto.getIdPerfilUsuario(),
                dto.getIdUsuario(),
                dto.getIdSexo(),
                dto.getDni(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getFechaNacimiento(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getIdDireccion(),
                dto.getIdRedSocial(),
                dto.getFotoPerfilId(),
                dto.isVerificado());
    }
}
