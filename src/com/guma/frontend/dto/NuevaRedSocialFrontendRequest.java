package com.guma.frontend.dto;

/**
 * Request para crear una nueva red social en el catálogo
 */
public class NuevaRedSocialFrontendRequest {
    private String nombre;
    private String link;

    public NuevaRedSocialFrontendRequest() {
    }

    public NuevaRedSocialFrontendRequest(String nombre, String link) {
        this.nombre = nombre;
        this.link = link;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
