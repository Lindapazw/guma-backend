package com.guma.frontend.dto;

/**
 * DTO para catálogo de tipos de alimentación (TIPO_ALIMENTACION)
 */
public class TipoAlimentacionFrontendDTO {
    private Integer id;
    private String descripcion;

    public TipoAlimentacionFrontendDTO() {
    }

    public TipoAlimentacionFrontendDTO(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion != null ? descripcion : "";
    }
}
