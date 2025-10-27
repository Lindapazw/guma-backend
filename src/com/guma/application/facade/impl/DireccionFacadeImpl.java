package com.guma.application.facade.impl;

import com.guma.application.dto.*;
import com.guma.application.facade.DireccionFacade;
import com.guma.application.factory.ServiceFactory;
import com.guma.application.mapper.CatalogoGeograficoMapper;
import com.guma.application.mapper.DireccionMapper;
import com.guma.backend.services.DireccionService;
import com.guma.domain.entities.*;
import com.guma.domain.exceptions.EntidadNoEncontradaException;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementación del facade de direcciones.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class DireccionFacadeImpl implements DireccionFacade {
    
    private final DireccionService direccionService;
    
    public DireccionFacadeImpl() {
        this.direccionService = ServiceFactory.crearDireccionService();
    }
    
    public DireccionFacadeImpl(DireccionService direccionService) {
        this.direccionService = direccionService;
    }
    
    @Override
    public ResultadoDTO<DireccionDTO> crear(DireccionDTO direccionDTO) {
        try {
            // Convertir DTO a entidad
            Direccion direccion = DireccionMapper.toEntity(direccionDTO);
            
            // Crear dirección
            Direccion direccionCreada = direccionService.crearDireccion(direccion);
            
            // Enriquecer con información de localidad, provincia y país
            Localidad localidad = direccionService.buscarLocalidadPorId(direccionCreada.getIdLocalidad());
            Provincia provincia = direccionService.buscarProvinciaPorId(localidad.getIdProvincia());
            Pais pais = direccionService.buscarPaisPorId(provincia.getIdPais());
            
            DireccionDTO dto = DireccionMapper.toDTO(direccionCreada);
            dto.setNombreLocalidad(localidad.getNombre());
            dto.setNombreProvincia(provincia.getNombre());
            dto.setNombrePais(pais.getNombre());
            
            return ResultadoDTO.exito(dto);
            
        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (SQLException e) {
            return ResultadoDTO.error("Error al crear dirección: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoDTO.error("Error inesperado: " + e.getMessage());
        }
    }
    
    @Override
    public ResultadoDTO<DireccionDTO> buscarPorId(Integer id) {
        try {
            Direccion direccion = direccionService.buscarPorId(id);
            DireccionDTO dto = DireccionMapper.toDTO(direccion);
            
            // Enriquecer con nombres de localidad/provincia/país
            Localidad localidad = direccionService.buscarLocalidadPorId(direccion.getIdLocalidad());
            dto.setNombreLocalidad(localidad.getNombre());
            
            return ResultadoDTO.exito(dto);
            
        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Dirección no encontrada");
        } catch (Exception e) {
            return ResultadoDTO.error("Error al buscar dirección: " + e.getMessage());
        }
    }
    
    @Override
    public ResultadoDTO<List<PaisDTO>> obtenerPaises() {
        try {
            List<Pais> paises = direccionService.obtenerPaises();
            List<PaisDTO> paisesDTO = CatalogoGeograficoMapper.toPaisDTOList(paises);
            return ResultadoDTO.exito(paisesDTO);
        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener países: " + e.getMessage());
        }
    }
    
    @Override
    public ResultadoDTO<List<ProvinciaDTO>> obtenerProvinciasPorPais(Integer idPais) {
        try {
            List<Provincia> provincias = direccionService.obtenerProvinciasPorPais(idPais);
            List<ProvinciaDTO> provinciasDTO = CatalogoGeograficoMapper.toProvinciaDTOList(provincias);
            return ResultadoDTO.exito(provinciasDTO);
        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener provincias: " + e.getMessage());
        }
    }
    
    @Override
    public ResultadoDTO<List<LocalidadDTO>> obtenerLocalidadesPorProvincia(Integer idProvincia) {
        try {
            List<Localidad> localidades = direccionService.obtenerLocalidadesPorProvincia(idProvincia);
            List<LocalidadDTO> localidadesDTO = CatalogoGeograficoMapper.toLocalidadDTOList(localidades);
            return ResultadoDTO.exito(localidadesDTO);
        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener localidades: " + e.getMessage());
        }
    }
}
