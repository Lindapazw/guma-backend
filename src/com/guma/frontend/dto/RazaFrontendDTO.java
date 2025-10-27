package com.guma.frontend.dto;

/**
 * DTO para catálogo de razas (RAZAS)
 */
public class RazaFrontendDTO {
    private Integer id;
    private String nombre;
    private Integer idEspecie;

    public RazaFrontendDTO() {
    }

    public RazaFrontendDTO(Integer id, String nombre, Integer idEspecie) {
        this.id = id;
        this.nombre = nombre;
        this.idEspecie = idEspecie;
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

    public Integer getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
