package com.guma.frontend.adapter;

import com.guma.application.dto.RedSocialDTO;
import com.guma.frontend.dto.NuevaRedSocialFrontendRequest;
import com.guma.frontend.dto.RedSocialFrontendDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador para convertir entre DTOs de RedSocial del frontend y de la aplicación.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class RedSocialAdapter {
    
    /**
     * Convierte un request del frontend a DTO de aplicación.
     */
    public static RedSocialDTO toApplicationDTO(NuevaRedSocialFrontendRequest request) {
        if (request == null) return null;
        
        RedSocialDTO dto = new RedSocialDTO();
        dto.setNombre(request.getNombre());
        dto.setLink(request.getLink());
        
        return dto;
    }
    
    /**
     * Convierte un DTO de aplicación a DTO del frontend.
     */
    public static RedSocialFrontendDTO toFrontendDTO(RedSocialDTO dto) {
        if (dto == null) return null;
        
        RedSocialFrontendDTO frontendDTO = new RedSocialFrontendDTO();
        frontendDTO.setId(dto.getIdRedSocial());
        frontendDTO.setNombre(dto.getNombre());
        frontendDTO.setUrl(dto.getLink());
        
        return frontendDTO;
    }
    
    /**
     * Convierte una lista de DTOs de aplicación a DTOs del frontend.
     */
    public static List<RedSocialFrontendDTO> toFrontendDTOList(List<RedSocialDTO> dtos) {
        return dtos.stream()
                .map(RedSocialAdapter::toFrontendDTO)
                .collect(Collectors.toList());
    }
}
