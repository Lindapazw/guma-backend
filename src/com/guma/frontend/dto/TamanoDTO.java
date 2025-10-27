package com.guma.frontend.dto;

/**
 * DTO para catálogo TAMANO
 * Valores: Miniatura, Pequeño, Mediano, Grande, Gigante
 * Usado en RF-04 (Alta de Mascota)
 */
public class TamanoDTO {
    private Integer id;
    private String tamano;

    public TamanoDTO() {}

    public TamanoDTO(Integer id, String tamano) {
        this.id = id;
        this.tamano = tamano;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    @Override
    public String toString() {
        return tamano != null ? tamano : "";
    }
}
