package com.guma.frontend.dto;

/**
 * DTO para catálogo COLOR
 * Valores: Negro, Blanco, Marrón, Gris, Dorado, Beige, Atigrado, Bicolor, Tricolor, Crema
 * Usado en RF-04 (Alta de Mascota)
 */
public class ColorDTO {
    private Integer id;
    private String color;

    public ColorDTO() {}

    public ColorDTO(Integer id, String color) {
        this.id = id;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color != null ? color : "";
    }
}
