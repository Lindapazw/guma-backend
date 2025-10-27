package com.guma.application.facade;

import com.guma.application.dto.RedSocialDTO;
import com.guma.application.dto.ResultadoDTO;

import java.util.List;

/**
 * Facade para operaciones de redes sociales.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface RedSocialFacade {
    
    /**
     * Crea una nueva red social.
     * 
     * @param redSocialDTO datos de la red social
     * @return ResultadoDTO con la red social creada
     */
    ResultadoDTO<RedSocialDTO> crear(RedSocialDTO redSocialDTO);
    
    /**
     * Busca una red social por su ID.
     * 
     * @param id identificador de la red social
     * @return ResultadoDTO con la red social encontrada
     */
    ResultadoDTO<RedSocialDTO> buscarPorId(Integer id);
    
    /**
     * Obtiene todas las redes sociales del catálogo.
     * 
     * @return ResultadoDTO con lista de redes sociales
     */
    ResultadoDTO<List<RedSocialDTO>> obtenerTodas();
}
