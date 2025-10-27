package com.guma.application.mapper;

import com.guma.application.dto.DireccionDTO;
import com.guma.domain.entities.Direccion;

/**
 * Mapper para convertir entre Direccion (entidad) y DireccionDTO.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class DireccionMapper {
    
    public static DireccionDTO toDTO(Direccion direccion) {
        if (direccion == null) return null;
        
        DireccionDTO dto = new DireccionDTO();
        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setNombre(direccion.getNombre());
        dto.setCodigoPostal(direccion.getCodigoPostal());
        dto.setCalle(direccion.getCalle());
        dto.setNumero(direccion.getNumero());
        dto.setDepto(direccion.getDepto());
        dto.setReferencia(direccion.getReferencia());
        dto.setLatitud(direccion.getLatitud());
        dto.setLongitud(direccion.getLongitud());
        dto.setIdLocalidad(direccion.getIdLocalidad());
        
        return dto;
    }
    
    public static Direccion toEntity(DireccionDTO dto) {
        if (dto == null) return null;
        
        return new Direccion(
            dto.getIdDireccion(),
            dto.getNombre(),
            dto.getCodigoPostal(),
            dto.getCalle(),
            dto.getNumero(),
            dto.getDepto(),
            dto.getReferencia(),
            dto.getLatitud(),
            dto.getLongitud(),
            dto.getIdLocalidad()
        );
    }
}
