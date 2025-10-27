package com.guma.backend.services;

import com.guma.backend.ports.CatalogoGeograficoRepository;
import com.guma.backend.ports.DireccionRepository;
import com.guma.domain.entities.Direccion;
import com.guma.domain.entities.Localidad;
import com.guma.domain.entities.Pais;
import com.guma.domain.entities.Provincia;
import com.guma.domain.exceptions.EntidadNoEncontradaException;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio de backend para gestión de direcciones y catálogos geográficos.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class DireccionService {
    
    private final DireccionRepository direccionRepository;
    private final CatalogoGeograficoRepository catalogoRepository;
    
    public DireccionService(DireccionRepository direccionRepository,
                           CatalogoGeograficoRepository catalogoRepository) {
        if (direccionRepository == null || catalogoRepository == null) {
            throw new IllegalArgumentException("Los repositorios no pueden ser nulos");
        }
        this.direccionRepository = direccionRepository;
        this.catalogoRepository = catalogoRepository;
    }
    
    /**
     * Crea una nueva dirección.
     * 
     * @param direccion dirección a crear
     * @return dirección creada con ID generado
     * @throws SQLException si ocurre un error de BD
     * @throws EntidadNoEncontradaException si la localidad no existe
     */
    public Direccion crearDireccion(Direccion direccion) throws SQLException {
        // Validar que la localidad exista
        catalogoRepository.findLocalidadById(direccion.getIdLocalidad())
            .orElseThrow(() -> new EntidadNoEncontradaException(
                "Localidad", direccion.getIdLocalidad()));
        
        return direccionRepository.save(direccion);
    }
    
    /**
     * Busca una dirección por su ID.
     */
    public Direccion buscarPorId(Integer id) {
        return direccionRepository.findById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException("Direccion", id));
    }
    
    // ===== CATÁLOGOS GEOGRÁFICOS =====
    
    /**
     * Obtiene todos los países.
     */
    public List<Pais> obtenerPaises() {
        return catalogoRepository.findAllPaises();
    }
    
    /**
     * Obtiene las provincias de un país.
     */
    public List<Provincia> obtenerProvinciasPorPais(Integer idPais) {
        return catalogoRepository.findProvinciasByPais(idPais);
    }
    
    /**
     * Obtiene las localidades de una provincia.
     */
    public List<Localidad> obtenerLocalidadesPorProvincia(Integer idProvincia) {
        return catalogoRepository.findLocalidadesByProvincia(idProvincia);
    }
    
    /**
     * Busca una localidad por ID.
     */
    public Localidad buscarLocalidadPorId(Integer id) {
        return catalogoRepository.findLocalidadById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException("Localidad", id));
    }
    
    /**
     * Busca una provincia por ID.
     */
    public Provincia buscarProvinciaPorId(Integer id) {
        return catalogoRepository.findProvinciaById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException("Provincia", id));
    }
    
    /**
     * Busca un país por ID.
     */
    public Pais buscarPaisPorId(Integer id) {
        return catalogoRepository.findPaisById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException("Pais", id));
    }
}
