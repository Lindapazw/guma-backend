package com.guma.domain.exceptions;

/**
 * Excepción lanzada cuando se intenta usar una contraseña que no cumple
 * con los requisitos mínimos de seguridad.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PasswordDebilException extends BusinessException {

    private static final String CODIGO_ERROR = "PASSWORD_DEBIL";

    /**
     * Constructor con mensaje del requisito no cumplido.
     * 
     * @param mensaje descripción del requisito no cumplido
     */
    public PasswordDebilException(String mensaje) {
        super(CODIGO_ERROR, mensaje);
    }

    /**
     * Constructor por defecto con mensaje genérico.
     */
    public PasswordDebilException() {
        super(CODIGO_ERROR, "La contraseña no cumple con los requisitos mínimos de seguridad");
    }
}
