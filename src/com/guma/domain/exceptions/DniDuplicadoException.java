package com.guma.domain.exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un DNI que ya existe.
 * 
 * Esta excepción indica violación de la constraint UNIQUE en
 * PERFIL_USUARIOS.dni.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class DniDuplicadoException extends BusinessException {

    private static final String CODIGO_ERROR = "DNI_DUPLICADO";

    /**
     * Constructor que recibe el DNI duplicado.
     * 
     * @param dni el DNI que ya existe en el sistema
     */
    public DniDuplicadoException(String dni) {
        super(CODIGO_ERROR, "Ya existe un perfil registrado con el DNI: " + dni);
    }
}
