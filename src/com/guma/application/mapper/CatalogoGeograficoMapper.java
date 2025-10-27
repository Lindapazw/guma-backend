package com.guma.application.mapper;

import com.guma.application.dto.*;
import com.guma.domain.entities.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades geográficas y DTOs.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class CatalogoGeograficoMapper {
    
    // ===== PAIS =====
    
    public static PaisDTO toDTO(Pais pais) {
        if (pais == null) return null;
        return new PaisDTO(pais.getIdPais(), pais.getNombre());
    }
    
    public static List<PaisDTO> toPaisDTOList(List<Pais> paises) {
        return paises.stream()
                .map(CatalogoGeograficoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    // ===== PROVINCIA =====
    
    public static ProvinciaDTO toDTO(Provincia provincia) {
        if (provincia == null) return null;
        return new ProvinciaDTO(
            provincia.getIdProvincia(),
            provincia.getNombre(),
            provincia.getIdPais()
        );
    }
    
    public static List<ProvinciaDTO> toProvinciaDTOList(List<Provincia> provincias) {
        return provincias.stream()
                .map(CatalogoGeograficoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    // ===== LOCALIDAD =====
    
    public static LocalidadDTO toDTO(Localidad localidad) {
        if (localidad == null) return null;
        return new LocalidadDTO(
            localidad.getIdLocalidad(),
            localidad.getNombre(),
            localidad.getIdProvincia()
        );
    }
    
    public static List<LocalidadDTO> toLocalidadDTOList(List<Localidad> localidades) {
        return localidades.stream()
                .map(CatalogoGeograficoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
