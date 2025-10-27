package com.guma.application.dto;

/**
 * DTO para Provincia.
 */
public class ProvinciaDTO {
    private Integer idProvincia;
    private String nombre;
    private Integer idPais;
    
    public ProvinciaDTO() {
    }
    
    public ProvinciaDTO(Integer idProvincia, String nombre, Integer idPais) {
        this.idProvincia = idProvincia;
        this.nombre = nombre;
        this.idPais = idPais;
    }
    
    public Integer getIdProvincia() {
        return idProvincia;
    }
    
    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
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
}
