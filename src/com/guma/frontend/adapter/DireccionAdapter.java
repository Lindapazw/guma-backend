package com.guma.frontend.adapter;

import com.guma.application.dto.DireccionDTO;
import com.guma.frontend.dto.DireccionFrontendDTO;
import com.guma.frontend.dto.NuevaDireccionFrontendRequest;

/**
 * Adaptador para convertir entre DTOs de Direccion del frontend y de la aplicación.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class DireccionAdapter {
    
    /**
     * Convierte un request del frontend a DTO de aplicación.
     */
    public static DireccionDTO toApplicationDTO(NuevaDireccionFrontendRequest request) {
        if (request == null) return null;
        
        DireccionDTO dto = new DireccionDTO();
        dto.setNombre(request.getNombre());
        dto.setCodigoPostal(request.getCodigoPostal() != null ? request.getCodigoPostal().toString() : null);
        dto.setCalle(request.getCalle());
        dto.setNumero(request.getNumero() != null ? request.getNumero().toString() : null);
        dto.setDepto(request.getDepto());
        dto.setReferencia(request.getReferencia());
        dto.setIdLocalidad(request.getIdLocalidad());
        
        return dto;
    }
    
    /**
     * Convierte un DTO de aplicación a DTO del frontend.
     */
    public static DireccionFrontendDTO toFrontendDTO(DireccionDTO dto) {
        if (dto == null) return null;
        
        DireccionFrontendDTO frontendDTO = new DireccionFrontendDTO();
        frontendDTO.setId(dto.getIdDireccion());
        frontendDTO.setNombre(dto.getNombre());
        frontendDTO.setCodigoPostal(dto.getCodigoPostal() != null ? Integer.parseInt(dto.getCodigoPostal()) : null);
        frontendDTO.setCalle(dto.getCalle());
        frontendDTO.setNumero(dto.getNumero() != null ? Integer.parseInt(dto.getNumero()) : null);
        frontendDTO.setDepto(dto.getDepto());
        frontendDTO.setReferencia(dto.getReferencia());
        frontendDTO.setIdLocalidad(dto.getIdLocalidad());
        
        // Construir display completo si hay información disponible
        String display = dto.getDisplayCompleto();
        if (display != null && !display.isEmpty()) {
            frontendDTO.setDisplayCompleto(display);
        }
        
        return frontendDTO;
    }
}
