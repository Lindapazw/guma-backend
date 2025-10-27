package com.guma.frontend.dto;

/**
 * DTO para catálogo ESTADO_REPRODUCTIVOS
 * Valores: Entero, Castrado, Esterilizada, Gestante, En celo, Post parto
 * Usado en RF-04 (Alta de Mascota)
 */
public class EstadoReproductivoDTO {
    private Integer id;
    private String estado;

    public EstadoReproductivoDTO() {}

    public EstadoReproductivoDTO(Integer id, String estado) {
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
