package com.guma.domain.exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un email
 * que ya existe en el sistema.
 * 
 * Esta excepción indica violación de la constraint UNIQUE en USUARIOS.email.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class UsuarioDuplicadoException extends BusinessException {

    private static final String CODIGO_ERROR = "USUARIO_DUPLICADO";

    /**
     * Constructor que recibe el email duplicado.
     * 
     * @param email el email que ya existe en el sistema
     */
    public UsuarioDuplicadoException(String email) {
        super(CODIGO_ERROR, "Ya existe un usuario registrado con el email: " + email);
    }

    /**
     * Constructor con mensaje personalizado.
     * 
     * @param email   el email duplicado
     * @param mensaje mensaje personalizado
     */
    public UsuarioDuplicadoException(String email, String mensaje) {
        super(CODIGO_ERROR, mensaje);
    }
}
