package com.guma.application.dto;

/**
 * DTO para Localidad.
 */
public class LocalidadDTO {
    private Integer idLocalidad;
    private String nombre;
    private Integer idProvincia;
    
    public LocalidadDTO() {
    }
    
    public LocalidadDTO(Integer idLocalidad, String nombre, Integer idProvincia) {
        this.idLocalidad = idLocalidad;
        this.nombre = nombre;
        this.idProvincia = idProvincia;
    }
    
    public Integer getIdLocalidad() {
        return idLocalidad;
    }
    
    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
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
}
