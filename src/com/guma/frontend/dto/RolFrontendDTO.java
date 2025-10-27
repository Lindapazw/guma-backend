package com.guma.frontend.dto;

/**
 * DTO para catálogo de roles (ROLES)
 */
public class RolFrontendDTO {
    private Integer id;
    private String rol;

    public RolFrontendDTO() {
    }

    public RolFrontendDTO(Integer id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return rol != null ? rol : "";
    }
}
