package com.guma.domain.entities;

/**
 * Entidad de dominio que representa un país.
 * 
 * Mapea con la tabla PAISES de la base de datos.
 * Catálogo de países del sistema.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class Pais {
    
    private Integer idPais;
    private String nombre;
    
    public Pais() {
    }
    
    public Pais(Integer idPais, String nombre) {
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
    
    @Override
    public String toString() {
        return nombre;
    }
}
