package com.guma.backend.ports;

import java.io.IOException;

/**
 * Port para almacenamiento de archivos.
 * Define el contrato para guardar y recuperar archivos del sistema.
 * La implementación puede ser local, S3, Azure Blob, etc.
 */
public interface FileStorage {

    /**
     * Guarda un archivo y retorna la ruta ABSOLUTA donde se guardó.
     * 
     * @param tipo          Tipo de archivo (ej: "usuario_perfil", "mascota_foto")
     * @param idEntidad     ID de la entidad relacionada (ej: idUsuario, idMascota)
     * @param nombreArchivo Nombre original del archivo
     * @param contenido     Bytes del archivo
     * @return Ruta ABSOLUTA del archivo guardado (ej: "/Users/user/guma/data/usuarios/36/perfil.jpg")
     * @throws IOException Si hay error al guardar el archivo
     */
    String guardarArchivo(String tipo, int idEntidad, String nombreArchivo, byte[] contenido)
            throws IOException;

    /**
     * Lee un archivo y retorna su contenido en bytes.
     * Acepta tanto rutas absolutas como relativas.
     * 
     * @param ruta Ruta del archivo (absoluta o relativa)
     * @return Bytes del archivo
     * @throws IOException Si hay error al leer el archivo o no existe
     */
    byte[] leerArchivo(String ruta) throws IOException;

    /**
     * Elimina un archivo del sistema.
     * Acepta tanto rutas absolutas como relativas.
     * 
     * @param ruta Ruta del archivo (absoluta o relativa)
     * @return true si se eliminó, false si no existía
     * @throws IOException Si hay error al eliminar el archivo
     */
    boolean eliminarArchivo(String ruta) throws IOException;

    /**
     * Verifica si un archivo existe.
     * Acepta tanto rutas absolutas como relativas.
     * 
     * @param ruta Ruta del archivo (absoluta o relativa)
     * @return true si existe, false si no
     */
    boolean existeArchivo(String ruta);

    /**
     * Obtiene la ruta completa (absoluta) de un archivo.
     * Útil para debugging o logs.
     * 
     * @param rutaRelativa Ruta relativa del archivo
     * @return Ruta absoluta en el sistema de archivos
     */
    String obtenerRutaCompleta(String rutaRelativa);
}
