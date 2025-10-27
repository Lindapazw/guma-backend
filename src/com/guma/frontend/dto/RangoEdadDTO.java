package com.guma.frontend.dto;

/**
 * DTO para catálogo RANGO_EDAD
 * Valores: Cachorro, Joven, Adulto, Senior, Geriátrico
 * Usado en RF-04 (Alta de Mascota)
 */
public class RangoEdadDTO {
    private Integer id;
    private String rango;

    public RangoEdadDTO() {}

    public RangoEdadDTO(Integer id, String rango) {
        this.id = id;
        this.rango = rango;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    @Override
    public String toString() {
        return rango != null ? rango : "";
    }
}
