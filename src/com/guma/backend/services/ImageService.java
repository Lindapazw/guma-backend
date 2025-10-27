package com.guma.backend.services;

import java.io.IOException;
import java.util.Optional;

import com.guma.backend.ports.FileStorage;
import com.guma.backend.ports.ImageRepository;
import com.guma.data.storage.FileStorageConfig;
import com.guma.domain.entities.Image;
import com.guma.domain.exceptions.EntidadNoEncontradaException;

/**
 * Servicio que coordina el almacenamiento físico (FileStorage)
 * y lógico (ImageRepository) de imágenes.
 * 
 * Este servicio es el punto central para gestionar imágenes en el sistema.
 * Maneja tanto el archivo físico como su metadata en la base de datos.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class ImageService {

    private final FileStorage fileStorage;
    private final ImageRepository imageRepository;

    /**
     * Constructor que inyecta dependencias.
     * 
     * @param imageRepository repositorio de imágenes
     */
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        this.fileStorage = FileStorageConfig.getInstance();
    }

    /**
     * Guarda una imagen (físicamente y en BD).
     * 
     * @param tipo          tipo de imagen ("usuario_perfil", "mascota_foto")
     * @param idEntidad     ID de la entidad relacionada (idUsuario, idMascota)
     * @param nombreArchivo nombre del archivo original
     * @param contenido     bytes de la imagen
     * @return entidad Image con ID generado
     * @throws IOException si hay error al guardar el archivo físico
     */
    public Image guardarImagen(String tipo, int idEntidad, String nombreArchivo, byte[] contenido)
            throws IOException {

        // 1. Guardar archivo físicamente
        String rutaRelativa = fileStorage.guardarArchivo(tipo, idEntidad, nombreArchivo, contenido);

        // 2. Crear metadata
        Image image = new Image(rutaRelativa);

        // 3. Guardar en BD
        return imageRepository.save(image);
    }

    /**
     * Obtiene una imagen por su ID.
     * 
     * @param idImage ID de la imagen
     * @return Optional con la imagen si existe
     */
    public Optional<Image> obtenerPorId(Integer idImage) {
        return imageRepository.findById(idImage);
    }

    /**
     * Obtiene los bytes de una imagen.
     * 
     * @param idImage ID de la imagen
     * @return bytes del archivo
     * @throws IOException si hay error al leer el archivo
     */
    public byte[] obtenerBytesImagen(Integer idImage) throws IOException {
        Image image = imageRepository.findById(idImage)
                .orElseThrow(() -> new EntidadNoEncontradaException("Image", idImage));

        return fileStorage.leerArchivo(image.getLink());
    }

    /**
     * Obtiene los bytes de una imagen por su ruta.
     * 
     * @param rutaRelativa ruta relativa de la imagen
     * @return bytes del archivo
     * @throws IOException si hay error al leer el archivo
     */
    public byte[] obtenerBytesPorRuta(String rutaRelativa) throws IOException {
        return fileStorage.leerArchivo(rutaRelativa);
    }

    /**
     * Actualiza una imagen existente con nuevo contenido.
     * 
     * @param idImage        ID de la imagen a actualizar
     * @param nuevoContenido nuevos bytes de la imagen
     * @param nuevoNombre    nuevo nombre del archivo
     * @return imagen actualizada
     * @throws IOException si hay error al guardar el archivo
     */
    public Image actualizarImagen(Integer idImage, byte[] nuevoContenido, String nuevoNombre)
            throws IOException {

        // 1. Buscar imagen existente
        Image image = imageRepository.findById(idImage)
                .orElseThrow(() -> new EntidadNoEncontradaException("Image", idImage));

        // 2. Eliminar archivo anterior
        fileStorage.eliminarArchivo(image.getLink());

        // 3. Guardar nuevo archivo (reutilizando misma ruta base si es posible)
        // Extraer tipo e idEntidad de la ruta anterior
        int idEntidad = extraerIdEntidad(image.getLink());
        String tipo = extraerTipo(image.getLink());

        String rutaRelativa = fileStorage.guardarArchivo(
                tipo,
                idEntidad,
                nuevoNombre,
                nuevoContenido);

        // 4. Actualizar metadata
        image.setLink(rutaRelativa);

        return imageRepository.update(image);
    }

    /**
     * Elimina una imagen (marca como inactiva el archivo físico pero mantiene el
     * registro en BD).
     * 
     * @param idImage ID de la imagen
     * @return true si se eliminó el archivo físico
     * @throws IOException si hay error al eliminar
     */
    public boolean eliminarImagen(Integer idImage) throws IOException {
        Image image = imageRepository.findById(idImage)
                .orElseThrow(() -> new EntidadNoEncontradaException("Image", idImage));

        // Eliminar archivo físico
        return fileStorage.eliminarArchivo(image.getLink());
    }

    /**
     * Elimina una imagen completamente (archivo físico + registro en BD).
     * ADVERTENCIA: Esta operación no se puede deshacer.
     * 
     * @param idImage ID de la imagen
     * @return true si se eliminó
     * @throws IOException si hay error al eliminar el archivo
     */
    public boolean eliminarImagenCompletamente(Integer idImage) throws IOException {
        // 1. Obtener imagen
        Optional<Image> imageOpt = imageRepository.findById(idImage);
        if (imageOpt.isEmpty()) {
            return false;
        }

        Image image = imageOpt.get();

        // 2. Eliminar archivo físico
        fileStorage.eliminarArchivo(image.getLink());

        // 3. Eliminar de BD
        return imageRepository.delete(idImage);
    }

    /**
     * Verifica si una imagen existe.
     * 
     * @param idImage ID de la imagen
     * @return true si existe
     */
    public boolean existe(Integer idImage) {
        return imageRepository.existsById(idImage);
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Extrae el ID de la entidad desde la ruta.
     * Ejemplo: "usuarios/36/perfil.jpg" → 36
     */
    private int extraerIdEntidad(String rutaRelativa) {
        String[] partes = rutaRelativa.split("/");
        if (partes.length >= 2) {
            try {
                return Integer.parseInt(partes[1]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Extrae el tipo de una ruta relativa.
     * Ejemplo: "usuarios/36/perfil.jpg" → "usuarios"
     */
    private String extraerTipo(String rutaRelativa) {
        String[] partes = rutaRelativa.split("/");
        if (partes.length >= 1) {
            return partes[0];
        }
        throw new IllegalArgumentException("Ruta inválida: " + rutaRelativa);
    }
}
