package com.guma.frontend.dto;

/**
 * DTO para catálogo de Redes Sociales
 */
public class RedSocialFrontendDTO {
    private Integer id;
    private String nombre;
    private String url;
    
    public RedSocialFrontendDTO() {
    }
    
    public RedSocialFrontendDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public RedSocialFrontendDTO(Integer id, String nombre, String url) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
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
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return nombre; // Para mostrar en JComboBox
    }
}
