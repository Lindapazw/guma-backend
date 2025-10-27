package com.guma.frontend.dto;

/**
 * DTO para direcciones (DIRECCIONES)
 */
public class DireccionFrontendDTO {
    private Integer id;
    private String nombre;
    private Integer codigoPostal;
    private String calle;
    private Integer numero;
    private String depto;
    private String referencia;
    private Double latitud;
    private Double longitud;
    private Integer idLocalidad;
    
    // Campos helper para mostrar información completa
    private String localidadNombre;
    private String provinciaNombre;
    private String paisNombre;
    private String displayCompleto;

    public DireccionFrontendDTO() {
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

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
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

    public String getLocalidadNombre() {
        return localidadNombre;
    }

    public void setLocalidadNombre(String localidadNombre) {
        this.localidadNombre = localidadNombre;
    }

    public String getProvinciaNombre() {
        return provinciaNombre;
    }

    public void setProvinciaNombre(String provinciaNombre) {
        this.provinciaNombre = provinciaNombre;
    }

    public String getPaisNombre() {
        return paisNombre;
    }

    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }

    public String getDisplayCompleto() {
        if (displayCompleto != null) {
            return displayCompleto;
        }
        
        // Generar formato normalizado: {calle} {numero|S/N}{, depto?} — {localidad}, {provincia}, {país} ({cp})
        StringBuilder sb = new StringBuilder();
        
        if (calle != null) {
            sb.append(calle).append(" ");
            if (numero != null) {
                sb.append(numero);
            } else {
                sb.append("S/N");
            }
        }
        
        if (depto != null && !depto.trim().isEmpty()) {
            sb.append(", ").append(depto);
        }
        
        if (localidadNombre != null || provinciaNombre != null || paisNombre != null) {
            sb.append(" — ");
            if (localidadNombre != null) {
                sb.append(localidadNombre);
            }
            if (provinciaNombre != null) {
                if (localidadNombre != null) sb.append(", ");
                sb.append(provinciaNombre);
            }
            if (paisNombre != null) {
                if (provinciaNombre != null || localidadNombre != null) sb.append(", ");
                sb.append(paisNombre);
            }
        }
        
        if (codigoPostal != null) {
            sb.append(" (").append(codigoPostal).append(")");
        }
        
        return sb.toString();
    }

    public void setDisplayCompleto(String displayCompleto) {
        this.displayCompleto = displayCompleto;
    }

    @Override
    public String toString() {
        return getDisplayCompleto();
    }
}
