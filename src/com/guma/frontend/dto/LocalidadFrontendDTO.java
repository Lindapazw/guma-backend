package com.guma.frontend.dto;

/**
 * DTO para localidad (LOCALIDADES)
 */
public class LocalidadFrontendDTO {
    private Integer id;
    private String nombre;
    private Integer idProvincia;

    public LocalidadFrontendDTO() {
    }

    public LocalidadFrontendDTO(Integer id, String nombre, Integer idProvincia) {
        this.id = id;
        this.nombre = nombre;
        this.idProvincia = idProvincia;
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

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
