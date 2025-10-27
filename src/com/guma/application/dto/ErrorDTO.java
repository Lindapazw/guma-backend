package com.guma.application.dto;

/**
 * DTO para representar errores de validación o de negocio.
 * Contiene un código, campo y un mensaje de error asociado.
 * 
 * Ejemplos:
 * - ErrorDTO("INVALID_EMAIL", "El email ya está registrado", "email")
 * - ErrorDTO("WEAK_PASSWORD", "La contraseña debe tener al menos 8 caracteres",
 * "password")
 * - ErrorDTO("DB_ERROR", "Error al conectar con la base de datos", null)
 * 
 * @author GUMA Development Team
 * @version 1.1 - Actualizado para compatibilidad con frontend
 */
public class ErrorDTO {

    /**
     * Método helper para crear ErrorDTO desde campo y mensaje.
     * Genera un código basado en el campo.
     * 
     * @param campo   Campo relacionado con el error
     * @param mensaje Mensaje del error
     * @return ErrorDTO con código generado
     */
    public static ErrorDTO deCampo(String campo, String mensaje) {
        String codigo = generarCodigo(campo);
        return new ErrorDTO(codigo, mensaje, campo);
    }

    /**
     * Genera un código de error basado en el nombre del campo.
     * 
     * @param campo Nombre del campo
     * @return Código de error generado
     */
    private static String generarCodigo(String campo) {
        if (campo == null || campo.equals("general")) {
            return "GENERAL_ERROR";
        }
        return "INVALID_" + campo.toUpperCase();
    }

    private String codigo;

    private String mensaje;

    private String campo;

    /**
     * Constructor vacío para frameworks.
     */
    public ErrorDTO() {
    }

    /**
     * Constructor completo del error.
     * 
     * @param codigo  Código de error (ej: "INVALID_EMAIL", "USER_NOT_FOUND")
     * @param mensaje Mensaje descriptivo del error
     * @param campo   Campo relacionado con el error (puede ser null para errores
     *                generales)
     */
    public ErrorDTO(String codigo, String mensaje, String campo) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.campo = campo;
    }

    /**
     * Constructor para errores generales (sin campo específico).
     * 
     * @param codigo  Código de error
     * @param mensaje Mensaje descriptivo del error
     */
    public ErrorDTO(String codigo, String mensaje) {
        this(codigo, mensaje, null);
    }

    /**
     * Obtiene el código de error.
     * 
     * @return Código del error
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de error.
     * 
     * @param codigo Código del error
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el campo relacionado con el error.
     * 
     * @return Nombre del campo (puede ser null)
     */
    public String getCampo() {
        return campo;
    }

    /**
     * Establece el campo relacionado con el error.
     * 
     * @param campo Nombre del campo
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * Obtiene el mensaje de error.
     * 
     * @return Mensaje descriptivo
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de error.
     * 
     * @param mensaje Mensaje descriptivo
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ErrorDTO{campo='" + campo + "', mensaje='" + mensaje + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ErrorDTO errorDTO = (ErrorDTO) o;
        return campo.equals(errorDTO.campo) && mensaje.equals(errorDTO.mensaje);
    }

    @Override
    public int hashCode() {
        int result = campo.hashCode();
        result = 31 * result + mensaje.hashCode();
        return result;
    }
}
