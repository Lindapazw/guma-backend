package com.guma.domain.entities;

/**
 * Entidad de dominio que representa una red social.
 * 
 * Mapea con la tabla REDES_SOCIALES de la base de datos.
 * Catálogo de redes sociales disponibles en el sistema.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class RedSocial {
    
    private Integer idRedSocial;
    private String nombre;
    private String link; // URL oficial de la red social
    
    /**
     * Constructor vacío.
     */
    public RedSocial() {
    }
    
    /**
     * Constructor completo.
     * 
     * @param idRedSocial identificador único (puede ser null para nuevas)
     * @param nombre nombre de la red social (ej: "Instagram")
     * @param link URL oficial de la red social
     */
    public RedSocial(Integer idRedSocial, String nombre, String link) {
        this.idRedSocial = idRedSocial;
        this.nombre = nombre;
        this.link = link;
    }
    
    /**
     * Constructor sin ID para nueva red social.
     */
    public RedSocial(String nombre, String link) {
        this(null, nombre, link);
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
    
    @Override
    public String toString() {
        return "RedSocial{" +
                "idRedSocial=" + idRedSocial +
                ", nombre='" + nombre + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
