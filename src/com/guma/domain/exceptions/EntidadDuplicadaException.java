package com.guma.domain.exceptions;

/**
 * Excepción lanzada cuando se intenta crear una entidad que ya existe.
 * Por ejemplo, una red social con nombre o link duplicado.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class EntidadDuplicadaException extends BusinessException {
    
    public EntidadDuplicadaException(String mensaje) {
        super(mensaje);
    }
    
    public EntidadDuplicadaException(String entidad, String campo, String valor) {
        super(String.format("%s con %s '%s' ya existe", entidad, campo, valor));
    }
}
