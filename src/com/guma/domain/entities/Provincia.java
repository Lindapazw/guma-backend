package com.guma.domain.entities;

/**
 * Entidad de dominio que representa una provincia/estado.
 * 
 * Mapea con la tabla PROVINCIAS de la base de datos.
 * Catálogo de provincias/estados del sistema.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class Provincia {
    
    private Integer idProvincia;
    private String nombre;
    private Integer idPais;
    
    public Provincia() {
    }
    
    public Provincia(Integer idProvincia, String nombre, Integer idPais) {
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
    
    @Override
    public String toString() {
        return nombre;
    }
}
