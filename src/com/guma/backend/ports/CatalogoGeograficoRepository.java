package com.guma.backend.ports;

import com.guma.domain.entities.Pais;
import com.guma.domain.entities.Provincia;
import com.guma.domain.entities.Localidad;

import java.util.List;
import java.util.Optional;

/**
 * Puerto (interface) para repositorios de catálogos geográficos.
 * 
 * Agrupa las operaciones de consulta sobre países, provincias y localidades.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface CatalogoGeograficoRepository {
    
    // ===== PAISES =====
    
    /**
     * Obtiene todos los países.
     * 
     * @return lista de países
     */
    List<Pais> findAllPaises();
    
    /**
     * Busca un país por su ID.
     * 
     * @param id identificador del país
     * @return Optional con el país si existe
     */
    Optional<Pais> findPaisById(Integer id);
    
    // ===== PROVINCIAS =====
    
    /**
     * Obtiene todas las provincias de un país.
     * 
     * @param idPais identificador del país
     * @return lista de provincias del país
     */
    List<Provincia> findProvinciasByPais(Integer idPais);
    
    /**
     * Busca una provincia por su ID.
     * 
     * @param id identificador de la provincia
     * @return Optional con la provincia si existe
     */
    Optional<Provincia> findProvinciaById(Integer id);
    
    // ===== LOCALIDADES =====
    
    /**
     * Obtiene todas las localidades de una provincia.
     * 
     * @param idProvincia identificador de la provincia
     * @return lista de localidades de la provincia
     */
    List<Localidad> findLocalidadesByProvincia(Integer idProvincia);
    
    /**
     * Busca una localidad por su ID.
     * 
     * @param id identificador de la localidad
     * @return Optional con la localidad si existe
     */
    Optional<Localidad> findLocalidadById(Integer id);
}
