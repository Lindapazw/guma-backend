package com.guma.domain.entities;

/**
 * Entidad de dominio que representa una dirección física.
 * 
 * Mapea con la tabla DIRECCIONES de la base de datos.
 * Contiene información completa de ubicación geográfica.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class Direccion {
    
    private Integer idDireccion;
    private String nombre;
    private String codigoPostal;
    private String calle;
    private String numero;
    private String depto;
    private String referencia;
    private Double latitud;
    private Double longitud;
    private Integer idLocalidad;
    
    /**
     * Constructor vacío.
     */
    public Direccion() {
    }
    
    /**
     * Constructor completo para crear una dirección.
     * 
     * @param idDireccion identificador único (puede ser null para nuevas)
     * @param nombre nombre descriptivo de la dirección
     * @param codigoPostal código postal
     * @param calle nombre de la calle
     * @param numero número de calle
     * @param depto departamento/piso (opcional)
     * @param referencia referencia adicional (opcional)
     * @param latitud coordenada de latitud (opcional)
     * @param longitud coordenada de longitud (opcional)
     * @param idLocalidad FK a localidades
     */
    public Direccion(Integer idDireccion, String nombre, String codigoPostal, String calle,
                     String numero, String depto, String referencia, Double latitud,
                     Double longitud, Integer idLocalidad) {
        this.idDireccion = idDireccion;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.calle = calle;
        this.numero = numero;
        this.depto = depto;
        this.referencia = referencia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idLocalidad = idLocalidad;
    }
    
    /**
     * Constructor simplificado para nueva dirección (sin ID).
     */
    public Direccion(String nombre, String codigoPostal, String calle, String numero,
                     String depto, String referencia, Integer idLocalidad) {
        this(null, nombre, codigoPostal, calle, numero, depto, referencia, null, null, idLocalidad);
    }
    
    // Getters y Setters
    
    public Integer getIdDireccion() {
        return idDireccion;
    }
    
    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCodigoPostal() {
        return codigoPostal;
    }
    
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
    public String getCalle() {
        return calle;
    }
    
    public void setCalle(String calle) {
        this.calle = calle;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getDepto() {
        return depto;
    }
    
    public void setDepto(String depto) {
        this.depto = depto;
    }
    
    public String getReferencia() {
        return referencia;
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    public Double getLatitud() {
        return latitud;
    }
    
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    
    public Double getLongitud() {
        return longitud;
    }
    
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    
    public Integer getIdLocalidad() {
        return idLocalidad;
    }
    
    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }
    
    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", nombre='" + nombre + '\'' +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", idLocalidad=" + idLocalidad +
                '}';
    }
}
