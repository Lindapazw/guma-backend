package com.guma.frontend.adapter;

import com.guma.application.dto.*;
import com.guma.frontend.dto.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador para convertir entre DTOs del frontend y DTOs de la aplicación.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class CatalogoGeograficoAdapter {
    
    // ===== PAIS =====
    
    public static PaisFrontendDTO toFrontendDTO(PaisDTO dto) {
        if (dto == null) return null;
        return new PaisFrontendDTO(dto.getIdPais(), dto.getNombre());
    }
    
    public static List<PaisFrontendDTO> toPaisesFrontendList(List<PaisDTO> dtos) {
        return dtos.stream()
                .map(CatalogoGeograficoAdapter::toFrontendDTO)
                .collect(Collectors.toList());
    }
    
    // ===== PROVINCIA =====
    
    public static ProvinciaFrontendDTO toFrontendDTO(ProvinciaDTO dto) {
        if (dto == null) return null;
        return new ProvinciaFrontendDTO(dto.getIdProvincia(), dto.getNombre(), dto.getIdPais());
    }
    
    public static List<ProvinciaFrontendDTO> toProvinciasFrontendList(List<ProvinciaDTO> dtos) {
        return dtos.stream()
                .map(CatalogoGeograficoAdapter::toFrontendDTO)
                .collect(Collectors.toList());
    }
    
    // ===== LOCALIDAD =====
    
    public static LocalidadFrontendDTO toFrontendDTO(LocalidadDTO dto) {
        if (dto == null) return null;
        return new LocalidadFrontendDTO(dto.getIdLocalidad(), dto.getNombre(), dto.getIdProvincia());
    }
    
    public static List<LocalidadFrontendDTO> toLocalidadesFrontendList(List<LocalidadDTO> dtos) {
        return dtos.stream()
                .map(CatalogoGeograficoAdapter::toFrontendDTO)
                .collect(Collectors.toList());
    }
}
