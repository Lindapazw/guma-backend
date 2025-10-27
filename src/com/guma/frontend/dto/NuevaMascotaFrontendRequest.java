package com.guma.frontend.dto;

import java.time.LocalDate;

/**
 * Request DTO para crear una nueva mascota
 * Usado en RF-04 (Alta de Mascota)
 * 
 * Validaciones frontend:
 * - Nombre: min 2 caracteres, no solo espacios
 * - Especie, Raza, Sexo, Estado Vital: obligatorios
 * - Fecha XOR Edad: al menos uno requerido
 * - Peso: decimal positivo
 * - Última Fecha Celo y Número Crías: solo si Sexo=Hembra y Estado Reproductivo compatible
 */
public class NuevaMascotaFrontendRequest {
    // Obligatorios
    private String nombre;
    private Integer idRaza;
    private Integer idSexo;
    private Integer idEstadoVital;
    
    // XOR: uno de los dos
    private LocalDate fechaNacimiento;
    private Integer edadAproximada;
    
    // Opcionales
    private Double peso;
    private Integer idEstadoReproductivo;
    private Integer idNivelActividad;
    private Integer idTipoAlimentacion;
    private String alimentoDescripcion;
    private Integer idTemperamento;
    private LocalDate ultimaFechaCelo;
    private Integer numeroCrias;
    private String descripcion;
    private Integer imageId;
    private Integer idColor;
    private Integer idTamano;
    private Integer idRangoEdad;

    public NuevaMascotaFrontendRequest() {}

    // Getters y Setters
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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
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
}
