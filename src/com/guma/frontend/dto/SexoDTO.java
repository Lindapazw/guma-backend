package com.guma.frontend.dto;

/**
 * DTO para catálogo SEXOS
 * Valores: Macho, Hembra, Indeterminado
 * Usado en RF-04 (Alta de Mascota)
 */
public class SexoDTO {
    private Integer id;
    private String sexo;

    public SexoDTO() {}

    public SexoDTO(Integer id, String sexo) {
        this.id = id;
        this.sexo = sexo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return sexo != null ? sexo : "";
    }
}
