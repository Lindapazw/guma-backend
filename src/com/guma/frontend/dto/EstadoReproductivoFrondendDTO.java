package com.guma.frontend.dto;

/**
 * DTO para catálogo de estados reproductivos (ESTADO_REPRODUCTIVOS)
 */
public class EstadoReproductivoFrondendDTO {
    private Integer id;
    private String nombre;

    public EstadoReproductivoFrondendDTO() {
    }

    public EstadoReproductivoFrondendDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
