package com.guma.frontend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para mascota (MASCOTAS)
 * Mapea todos los campos para registro y consulta
 */
public class MascotaFrontendDTO {
    // Campos obligatorios
    private Integer id;
    private String nombre;
    private Integer idRaza;
    private Integer idSexo;
    private Integer idEstadoVital;
    
    // Campos opcionales
    private LocalDate fechaNacimiento;
    private Integer edadAproximada;
    private BigDecimal peso;
    private Integer idEstadoReproductivo;
    private Integer idNivelActividad;
    private Integer idTipoAlimentacion;
    private String alimentoDescripcion;
    private Integer idTemperamento;
    private LocalDate ultimaFechaCelo;
    private Integer numeroCrias;
    private String descripcion;
    private Integer imageId;
    private String qrId;
    private Integer idColor;
    private Integer idTamano;
    private Integer idRangoEdad;
    private Boolean activo;
    
    // Campos helper para mostrar en UI
    private String razaNombre;
    private String sexoDescripcion;
    private String estadoVitalDescripcion;
    private String especieNombre;

    public MascotaFrontendDTO() {
    }

    // Getters y Setters
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

    public Integer getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Integer idRaza) {
        this.idRaza = idRaza;
    }

    public Integer getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Integer idSexo) {
        this.idSexo = idSexo;
    }

    public Integer getIdEstadoVital() {
        return idEstadoVital;
    }

    public void setIdEstadoVital(Integer idEstadoVital) {
        this.idEstadoVital = idEstadoVital;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdadAproximada() {
        return edadAproximada;
    }

    public void setEdadAproximada(Integer edadAproximada) {
        this.edadAproximada = edadAproximada;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Integer getIdEstadoReproductivo() {
        return idEstadoReproductivo;
    }

    public void setIdEstadoReproductivo(Integer idEstadoReproductivo) {
        this.idEstadoReproductivo = idEstadoReproductivo;
    }

    public Integer getIdNivelActividad() {
        return idNivelActividad;
    }

    public void setIdNivelActividad(Integer idNivelActividad) {
        this.idNivelActividad = idNivelActividad;
    }

    public Integer getIdTipoAlimentacion() {
        return idTipoAlimentacion;
    }

    public void setIdTipoAlimentacion(Integer idTipoAlimentacion) {
        this.idTipoAlimentacion = idTipoAlimentacion;
    }

    public String getAlimentoDescripcion() {
        return alimentoDescripcion;
    }

    public void setAlimentoDescripcion(String alimentoDescripcion) {
        this.alimentoDescripcion = alimentoDescripcion;
    }

    public Integer getIdTemperamento() {
        return idTemperamento;
    }

    public void setIdTemperamento(Integer idTemperamento) {
        this.idTemperamento = idTemperamento;
    }

    public LocalDate getUltimaFechaCelo() {
        return ultimaFechaCelo;
    }

    public void setUltimaFechaCelo(LocalDate ultimaFechaCelo) {
        this.ultimaFechaCelo = ultimaFechaCelo;
    }

    public Integer getNumeroCrias() {
        return numeroCrias;
    }

    public void setNumeroCrias(Integer numeroCrias) {
        this.numeroCrias = numeroCrias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }

    public Integer getIdColor() {
        return idColor;
    }

    public void setIdColor(Integer idColor) {
        this.idColor = idColor;
    }

    public Integer getIdTamano() {
        return idTamano;
    }

    public void setIdTamano(Integer idTamano) {
        this.idTamano = idTamano;
    }

    public Integer getIdRangoEdad() {
        return idRangoEdad;
    }

    public void setIdRangoEdad(Integer idRangoEdad) {
        this.idRangoEdad = idRangoEdad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    // Helpers
    public String getRazaNombre() {
        return razaNombre;
    }

    public void setRazaNombre(String razaNombre) {
        this.razaNombre = razaNombre;
    }

    public String getSexoDescripcion() {
        return sexoDescripcion;
    }

    public void setSexoDescripcion(String sexoDescripcion) {
        this.sexoDescripcion = sexoDescripcion;
    }

    public String getEstadoVitalDescripcion() {
        return estadoVitalDescripcion;
    }

    public void setEstadoVitalDescripcion(String estadoVitalDescripcion) {
        this.estadoVitalDescripcion = estadoVitalDescripcion;
    }

    public String getEspecieNombre() {
        return especieNombre;
    }

    public void setEspecieNombre(String especieNombre) {
        this.especieNombre = especieNombre;
    }

    @Override
    public String toString() {
        return "MascotaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", raza='" + razaNombre + '\'' +
                ", especie='" + especieNombre + '\'' +
                '}';
    }
}
