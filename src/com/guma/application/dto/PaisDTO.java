package com.guma.application.dto;

/**
 * DTO para Pais.
 */
public class PaisDTO {
    private Integer idPais;
    private String nombre;
    
    public PaisDTO() {
    }
    
    public PaisDTO(Integer idPais, String nombre) {
        this.idPais = idPais;
        this.nombre = nombre;
    }
    
    public Integer getIdPais() {
        return idPais;
    }
    
    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
