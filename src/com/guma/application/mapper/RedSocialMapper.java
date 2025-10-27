package com.guma.application.mapper;

import com.guma.application.dto.RedSocialDTO;
import com.guma.domain.entities.RedSocial;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre RedSocial (entidad) y RedSocialDTO.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class RedSocialMapper {
    
    public static RedSocialDTO toDTO(RedSocial redSocial) {
        if (redSocial == null) return null;
        
        return new RedSocialDTO(
            redSocial.getIdRedSocial(),
            redSocial.getNombre(),
            redSocial.getLink()
        );
    }
    
    public static List<RedSocialDTO> toDTOList(List<RedSocial> redesSociales) {
        return redesSociales.stream()
                .map(RedSocialMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public static RedSocial toEntity(RedSocialDTO dto) {
        if (dto == null) return null;
        
        return new RedSocial(
            dto.getIdRedSocial(),
            dto.getNombre(),
            dto.getLink()
        );
    }
}
