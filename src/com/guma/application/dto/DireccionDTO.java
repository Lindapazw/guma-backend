package com.guma.application.dto;

/**
 * DTO para transferir información de Direccion entre capas.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class DireccionDTO {
    
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
    
    // Campos de navegación (para display)
    private String nombreLocalidad;
    private String nombreProvincia;
    private String nombrePais;
    
    public DireccionDTO() {
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
    
    public String getNombreLocalidad() {
        return nombreLocalidad;
    }
    
    public void setNombreLocalidad(String nombreLocalidad) {
        this.nombreLocalidad = nombreLocalidad;
    }
    
    public String getNombreProvincia() {
        return nombreProvincia;
    }
    
    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }
    
    public String getNombrePais() {
        return nombrePais;
    }
    
    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }
    
    /**
     * Obtiene el display completo de la dirección.
     * Formato: "Calle Número, Localidad, Provincia, País"
     */
    public String getDisplayCompleto() {
        StringBuilder sb = new StringBuilder();
        
        if (calle != null) {
            sb.append(calle);
            if (numero != null) {
                sb.append(" ").append(numero);
            }
        }
        
        if (nombreLocalidad != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(nombreLocalidad);
        }
        
        if (nombreProvincia != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(nombreProvincia);
        }
        
        if (nombrePais != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(nombrePais);
        }
        
        return sb.toString();
    }
}
