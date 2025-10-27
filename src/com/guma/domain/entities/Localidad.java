package com.guma.domain.entities;

/**
 * Entidad de dominio que representa una localidad/ciudad.
 * 
 * Mapea con la tabla LOCALIDADES de la base de datos.
 * Catálogo de localidades del sistema.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class Localidad {
    
    private Integer idLocalidad;
    private String nombre;
    private Integer idProvincia;
    
    public Localidad() {
    }
    
    public Localidad(Integer idLocalidad, String nombre, Integer idProvincia) {
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
    
    @Override
    public String toString() {
        return nombre;
    }
}
