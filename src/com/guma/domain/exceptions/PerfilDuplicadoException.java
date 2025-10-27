package com.guma.domain.exceptions;

/**
 * Excepción lanzada cuando se intenta crear un perfil de usuario duplicado.
 * 
 * Un usuario solo puede tener un perfil asociado (relación 1:1).
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilDuplicadoException extends BusinessException {

    private static final String CODIGO_ERROR = "PERFIL_DUPLICADO";

    /**
     * Constructor que recibe el ID del usuario.
     * 
     * @param idUsuario el ID del usuario que ya tiene perfil
     */
    public PerfilDuplicadoException(Integer idUsuario) {
        super(CODIGO_ERROR, "El usuario con ID " + idUsuario + " ya tiene un perfil asociado");
    }

    /**
     * Constructor con mensaje personalizado.
     * 
     * @param mensaje mensaje descriptivo del error
     */
    public PerfilDuplicadoException(String mensaje) {
        super(CODIGO_ERROR, mensaje);
    }
}
