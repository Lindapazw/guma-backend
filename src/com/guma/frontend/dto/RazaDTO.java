package com.guma.frontend.dto;

/**
 * DTO para catálogo RAZAS
 * Relación: RAZAS.id_especie → ESPECIES.id_especie
 * Usado en RF-04 (Alta de Mascota)
 */
public class RazaDTO {
    private Integer id;
    private String nombre;
    private Integer idEspecie;

    public RazaDTO() {}

    public RazaDTO(Integer id, String nombre, Integer idEspecie) {
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
