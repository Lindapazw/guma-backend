package com.guma.domain.exceptions;

/**
 * Excepción lanzada cuando se intenta usar un email con formato inválido.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class EmailInvalidoException extends BusinessException {

    private static final String CODIGO_ERROR = "EMAIL_INVALIDO";

    /**
     * Constructor que recibe el email inválido.
     * 
     * @param email el email con formato incorrecto
     */
    public EmailInvalidoException(String email) {
        super(CODIGO_ERROR, "El email no tiene un formato válido: " + email);
    }

    /**
     * Constructor con mensaje personalizado.
     * 
     * @param email   el email inválido
     * @param mensaje mensaje personalizado
     */
    public EmailInvalidoException(String email, String mensaje) {
        super(CODIGO_ERROR, mensaje);
    }
}
