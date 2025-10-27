package com.guma.frontend.dto;

/**
 * DTO para catálogo de estados vitales (ESTADO_VITAL)
 */
public class EstadoVitalFrontendDTO {
    private Integer id;
    private String estado;

    public EstadoVitalFrontendDTO() {
    }

    public EstadoVitalFrontendDTO(Integer id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return estado != null ? estado : "";
    }
}
