package com.guma.domain.exceptions;

/**
 * Excepción base para todas las excepciones de negocio del dominio GUMA.
 * 
 * Esta excepción representa errores de lógica de negocio que deben ser
 * capturados y manejados por las capas superiores (application, frontend).
 * 
 * Es una RuntimeException para evitar checked exceptions en el dominio,
 * siguiendo principios de Clean Architecture.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class BusinessException extends RuntimeException {

    private final String codigoError;

    /**
     * Constructor con mensaje.
     * 
     * @param mensaje mensaje descriptivo del error
     */
    public BusinessException(String mensaje) {
        super(mensaje);
        this.codigoError = null;
    }

    /**
     * Constructor con mensaje y causa.
     * 
     * @param mensaje mensaje descriptivo del error
     * @param causa   excepción que causó este error
     */
    public BusinessException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = null;
    }

    /**
     * Constructor con código de error y mensaje.
     * 
     * @param codigoError código único que identifica el tipo de error
     * @param mensaje     mensaje descriptivo del error
     */
    public BusinessException(String codigoError, String mensaje) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    /**
     * Constructor completo con código, mensaje y causa.
     * 
     * @param codigoError código único que identifica el tipo de error
     * @param mensaje     mensaje descriptivo del error
     * @param causa       excepción que causó este error
     */
    public BusinessException(String codigoError, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = codigoError;
    }

    /**
     * Obtiene el código de error asociado.
     * 
     * @return el código de error, o null si no tiene
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Verifica si esta excepción tiene un código de error.
     * 
     * @return true si tiene código, false en caso contrario
     */
    public boolean tieneCodigoError() {
        return codigoError != null && !codigoError.trim().isEmpty();
    }
}
