package com.guma.frontend.dto;

/**
 * DTO para catálogo NIVEL_ACTIVIDAD
 * Valores: Muy baja - sedentario, Baja - caminatas ocasionales, Media - actividad diaria moderada,
 *          Alta - perro deportivo o de trabajo, Extrema - entrenamiento competitivo
 * Usado en RF-04 (Alta de Mascota)
 */
public class NivelActividadDTO {
    private Integer id;
    private String nivel;

    public NivelActividadDTO() {}

    public NivelActividadDTO(Integer id, String nivel) {
        this.id = id;
        this.nivel = nivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return nivel != null ? nivel : "";
    }
}
