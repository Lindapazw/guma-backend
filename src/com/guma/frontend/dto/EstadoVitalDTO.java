package com.guma.frontend.dto;

/**
 * DTO para catálogo ESTADO_VITAL
 * Valores: Vivo, Fallecido, Desaparecido, En adopción, Adoptado, En tratamiento, Temporalmente fuera del hogar
 * Usado en RF-04 (Alta de Mascota)
 */
public class EstadoVitalDTO {
    private Integer id;
    private String estado;

    public EstadoVitalDTO() {}

    public EstadoVitalDTO(Integer id, String estado) {
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
