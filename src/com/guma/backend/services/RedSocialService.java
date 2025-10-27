package com.guma.backend.services;

import com.guma.backend.ports.RedSocialRepository;
import com.guma.domain.entities.RedSocial;
import com.guma.domain.exceptions.EntidadDuplicadaException;
import com.guma.domain.exceptions.EntidadNoEncontradaException;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio de backend para gestión de redes sociales.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class RedSocialService {
    
    private final RedSocialRepository redSocialRepository;
    
    public RedSocialService(RedSocialRepository redSocialRepository) {
        if (redSocialRepository == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo");
        }
        this.redSocialRepository = redSocialRepository;
    }
    
    /**
     * Crea una nueva red social.
     * 
     * @param redSocial red social a crear
     * @return red social creada con ID generado
     * @throws SQLException si ocurre un error de BD
     * @throws EntidadDuplicadaException si ya existe una red social con ese nombre o link
     */
    public RedSocial crearRedSocial(RedSocial redSocial) throws SQLException {
        // Validar que no exista una red social con el mismo nombre o link
        if (redSocialRepository.existsByNombreOrLink(redSocial.getNombre(), redSocial.getLink())) {
            throw new EntidadDuplicadaException(
                "Ya existe una red social con ese nombre o enlace");
        }
        
        return redSocialRepository.save(redSocial);
    }
    
    /**
     * Busca una red social por su ID.
     */
    public RedSocial buscarPorId(Integer id) {
        return redSocialRepository.findById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException("RedSocial", id));
    }
    
    /**
     * Obtiene todas las redes sociales del catálogo.
     */
    public List<RedSocial> obtenerTodas() {
        return redSocialRepository.findAll();
    }
}
