package com.guma.application.dto;

/**
 * DTO para transferir información de Red Social entre capas.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class RedSocialDTO {
    
    private Integer idRedSocial;
    private String nombre;
    private String link;
    
    public RedSocialDTO() {
    }
    
    public RedSocialDTO(Integer idRedSocial, String nombre, String link) {
        this.idRedSocial = idRedSocial;
        this.nombre = nombre;
        this.link = link;
    }
    
    // Getters y Setters
    
    public Integer getIdRedSocial() {
        return idRedSocial;
    }
    
    public void setIdRedSocial(Integer idRedSocial) {
        this.idRedSocial = idRedSocial;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
}
