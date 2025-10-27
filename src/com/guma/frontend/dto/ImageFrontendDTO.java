package com.guma.frontend.dto;

/**
 * DTO para imágenes (IMAGES)
 */
public class ImageFrontendDTO {
    private Integer id;
    private String link;

    public ImageFrontendDTO() {
    }

    public ImageFrontendDTO(Integer id, String link) {
        this.id = id;
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
