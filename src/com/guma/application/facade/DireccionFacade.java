package com.guma.application.facade;

import com.guma.application.dto.*;

import java.util.List;

/**
 * Facade para operaciones de direcciones y catálogos geográficos.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface DireccionFacade {
    
    /**
     * Crea una nueva dirección.
     * 
     * @param direccionDTO datos de la dirección
     * @return ResultadoDTO con la dirección creada
     */
    ResultadoDTO<DireccionDTO> crear(DireccionDTO direccionDTO);
    
    /**
     * Busca una dirección por su ID.
     * 
     * @param id identificador de la dirección
     * @return ResultadoDTO con la dirección encontrada
     */
    ResultadoDTO<DireccionDTO> buscarPorId(Integer id);
    
    // ===== CATÁLOGOS GEOGRÁFICOS =====
    
    /**
     * Obtiene todos los países.
     * 
     * @return ResultadoDTO con lista de países
     */
    ResultadoDTO<List<PaisDTO>> obtenerPaises();
    
    /**
     * Obtiene las provincias de un país.
     * 
     * @param idPais identificador del país
     * @return ResultadoDTO con lista de provincias
     */
    ResultadoDTO<List<ProvinciaDTO>> obtenerProvinciasPorPais(Integer idPais);
    
    /**
     * Obtiene las localidades de una provincia.
     * 
     * @param idProvincia identificador de la provincia
     * @return ResultadoDTO con lista de localidades
     */
    ResultadoDTO<List<LocalidadDTO>> obtenerLocalidadesPorProvincia(Integer idProvincia);
}
