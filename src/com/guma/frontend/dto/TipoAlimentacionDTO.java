package com.guma.frontend.dto;

/**
 * DTO para catálogo TIPO_ALIMENTACION
 * Valores: Balanceado seco, Balanceado húmedo, Casera controlada,
 *          Mixta (balanceado + casera), Medicada / veterinaria, Barf (cruda)
 * Usado en RF-04 (Alta de Mascota)
 */
public class TipoAlimentacionDTO {
    private Integer id;
    private String tipo;

    public TipoAlimentacionDTO() {}

    public TipoAlimentacionDTO(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo != null ? tipo : "";
    }
}
