package com.guma.application.facade.impl;

import com.guma.application.dto.RedSocialDTO;
import com.guma.application.dto.ResultadoDTO;
import com.guma.application.facade.RedSocialFacade;
import com.guma.application.factory.ServiceFactory;
import com.guma.application.mapper.RedSocialMapper;
import com.guma.backend.services.RedSocialService;
import com.guma.domain.entities.RedSocial;
import com.guma.domain.exceptions.EntidadDuplicadaException;
import com.guma.domain.exceptions.EntidadNoEncontradaException;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementación del facade de redes sociales.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class RedSocialFacadeImpl implements RedSocialFacade {
    
    private final RedSocialService redSocialService;
    
    public RedSocialFacadeImpl() {
        this.redSocialService = ServiceFactory.crearRedSocialService();
    }
    
    public RedSocialFacadeImpl(RedSocialService redSocialService) {
        this.redSocialService = redSocialService;
    }
    
    @Override
    public ResultadoDTO<RedSocialDTO> crear(RedSocialDTO redSocialDTO) {
        try {
            // Convertir DTO a entidad
            RedSocial redSocial = RedSocialMapper.toEntity(redSocialDTO);
            
            // Crear red social
            RedSocial redSocialCreada = redSocialService.crearRedSocial(redSocial);
            
            // Convertir a DTO y retornar
            RedSocialDTO dto = RedSocialMapper.toDTO(redSocialCreada);
            return ResultadoDTO.exito(dto);
            
        } catch (EntidadDuplicadaException e) {
            return ResultadoDTO.error(e.getMessage());
        } catch (SQLException e) {
            return ResultadoDTO.error("Error al crear red social: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoDTO.error("Error inesperado: " + e.getMessage());
        }
    }
    
    @Override
    public ResultadoDTO<RedSocialDTO> buscarPorId(Integer id) {
        try {
            RedSocial redSocial = redSocialService.buscarPorId(id);
            RedSocialDTO dto = RedSocialMapper.toDTO(redSocial);
            return ResultadoDTO.exito(dto);
            
        } catch (EntidadNoEncontradaException e) {
            return ResultadoDTO.error("Red social no encontrada");
        } catch (Exception e) {
            return ResultadoDTO.error("Error al buscar red social: " + e.getMessage());
        }
    }
    
    @Override
    public ResultadoDTO<List<RedSocialDTO>> obtenerTodas() {
        try {
            List<RedSocial> redesSociales = redSocialService.obtenerTodas();
            List<RedSocialDTO> redesDTO = RedSocialMapper.toDTOList(redesSociales);
            return ResultadoDTO.exito(redesDTO);
        } catch (Exception e) {
            return ResultadoDTO.error("Error al obtener redes sociales: " + e.getMessage());
        }
    }
}
