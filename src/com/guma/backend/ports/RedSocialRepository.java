package com.guma.backend.ports;

import com.guma.domain.entities.RedSocial;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Puerto (interface) para el repositorio de redes sociales.
 * 
 * Define el contrato para operaciones CRUD sobre redes sociales.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface RedSocialRepository {
    
    /**
     * Guarda una nueva red social.
     * 
     * @param redSocial red social a guardar
     * @return red social guardada con ID generado
     * @throws SQLException si ocurre un error de BD
     */
    RedSocial save(RedSocial redSocial) throws SQLException;
    
    /**
     * Busca una red social por su ID.
     * 
     * @param id identificador de la red social
     * @return Optional con la red social si existe
     */
    Optional<RedSocial> findById(Integer id);
    
    /**
     * Obtiene todas las redes sociales del catálogo.
     * 
     * @return lista de todas las redes sociales
     */
    List<RedSocial> findAll();
    
    /**
     * Verifica si existe una red social con el mismo nombre o link.
     * 
     * @param nombre nombre a verificar
     * @param link link a verificar
     * @return true si existe
     */
    boolean existsByNombreOrLink(String nombre, String link);
}
