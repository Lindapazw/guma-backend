package com.guma.frontend.dto;

/**
 * DTO para catálogo de especies (ESPECIES)
 */
public class EspecieFrontendDTO {
    private Integer id;
    private String especie;

    public EspecieFrontendDTO() {
    }

    public EspecieFrontendDTO(Integer id, String especie) {
        this.id = id;
        this.especie = especie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    @Override
    public String toString() {
        return especie != null ? especie : "";
    }
}
