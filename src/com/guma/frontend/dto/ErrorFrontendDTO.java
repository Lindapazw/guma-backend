package com.guma.frontend.dto;

/**
 * DTO para representar errores
 */
public class ErrorFrontendDTO {
    private String codigo;
    private String mensaje;
    private String campo;

    // Constructor vacío
    public ErrorFrontendDTO() {
    }

    // Constructor completo
    public ErrorFrontendDTO(String codigo, String mensaje, String campo) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.campo = campo;
    }

    // Constructor sin campo
    public ErrorFrontendDTO(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.campo = null;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
