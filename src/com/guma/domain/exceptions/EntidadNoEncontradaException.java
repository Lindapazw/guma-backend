package com.guma.domain.exceptions;

/**
 * Excepción lanzada cuando no se encuentra una entidad solicitada.
 * 
 * Ejemplo: buscar un usuario por ID que no existe en la base de datos.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class EntidadNoEncontradaException extends BusinessException {

    private static final String CODIGO_ERROR = "ENTIDAD_NO_ENCONTRADA";

    /**
     * Constructor que recibe el tipo de entidad y el identificador.
     * 
     * @param tipoEntidad   nombre de la entidad (ej: "Usuario", "Rol")
     * @param identificador valor del identificador buscado
     */
    public EntidadNoEncontradaException(String tipoEntidad, Object identificador) {
        super(CODIGO_ERROR, tipoEntidad + " no encontrado con identificador: " + identificador);
    }

    /**
     * Constructor con mensaje personalizado.
     * 
     * @param mensaje mensaje descriptivo del error
     */
    public EntidadNoEncontradaException(String mensaje) {
        super(CODIGO_ERROR, mensaje);
    }
}
