package com.guma.frontend.dto;

/**
 * DTO para catálogo TEMPERAMENTOS
 * Valores: Tranquilo, Sociable, Tímido, Curioso, Dominante, Protector, Juguetón, Independiente
 * Usado en RF-04 (Alta de Mascota)
 */
public class TemperamentoDTO {
    private Integer id;
    private String temperamento;

    public TemperamentoDTO() {}

    public TemperamentoDTO(Integer id, String temperamento) {
        this.id = id;
        this.temperamento = temperamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    @Override
    public String toString() {
        return temperamento != null ? temperamento : "";
    }
}
