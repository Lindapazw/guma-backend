package com.guma.backend.ports;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.guma.domain.entities.Image;

/**
 * Port para persistencia de imágenes.
 * Define el contrato para operaciones CRUD sobre referencias de imágenes.
 * 
 * La tabla IMAGES solo almacena: id_images, link
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface ImageRepository {

    /**
     * Guarda una nueva imagen en la base de datos.
     * 
     * @param image la imagen a guardar (solo necesita link)
     * @return la imagen guardada con su ID generado
     */
    Image save(Image image);
    
    /**
     * Guarda una nueva imagen usando una conexión existente (para transacciones).
     * 
     * @param image la imagen a guardar
     * @param conn conexión de la transacción
     * @return la imagen guardada con su ID generado
     * @throws SQLException si hay error en la BD
     */
    Image save(Image image, Connection conn) throws SQLException;

    /**
     * Busca una imagen por su ID.
     * 
     * @param id el ID de la imagen
     * @return Optional con la imagen si existe
     */
    Optional<Image> findById(Integer id);

    /**
     * Busca una imagen por su ruta (link).
     * 
     * @param link ruta relativa de la imagen
     * @return Optional con la imagen si existe
     */
    Optional<Image> findByLink(String link);

    /**
     * Actualiza una imagen existente.
     * 
     * @param image la imagen con datos actualizados
     * @return la imagen actualizada
     */
    Image update(Image image);

    /**
     * Elimina una imagen de forma física (hard delete).
     * Elimina el registro de la base de datos.
     * NOTA: El archivo físico debe eliminarse por separado usando FileStorage.
     * 
     * @param id el ID de la imagen a eliminar
     * @return true si se eliminó, false si no existe
     */
    boolean delete(Integer id);
    
    /**
     * Elimina una imagen usando una conexión existente (para transacciones).
     * 
     * @param id el ID de la imagen a eliminar
     * @param conn conexión de la transacción
     * @return true si se eliminó, false si no existe
     * @throws SQLException si hay error en la BD
     */
    boolean delete(Integer id, Connection conn) throws SQLException;

    /**
     * Verifica si existe una imagen con el ID especificado.
     * 
     * @param id el ID a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsById(Integer id);
}
