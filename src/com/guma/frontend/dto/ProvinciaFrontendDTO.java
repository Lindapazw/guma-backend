package com.guma.frontend.dto;

/**
 * DTO para provincia (PROVINCIAS)
 */
public class ProvinciaFrontendDTO {
    private Integer id;
    private String nombre;
    private Integer idPais;

    public ProvinciaFrontendDTO() {
    }

    public ProvinciaFrontendDTO(Integer id, String nombre, Integer idPais) {
        this.id = id;
        this.nombre = nombre;
        this.idPais = idPais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
