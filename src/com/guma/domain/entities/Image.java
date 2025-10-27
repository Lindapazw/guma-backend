package com.guma.domain.entities;

/**
 * Entidad que representa una imagen en el sistema.
 * Almacena la ruta de archivos de imagen (fotos de perfil, mascotas, etc.)
 * mientras que el archivo físico se guarda mediante FileStorage.
 * 
 * Corresponde a la tabla IMAGES con campos:
 * - id_images (INT PK)
 * - link (TEXT) - Ruta relativa al archivo físico
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class Image {

    private Integer idImage;
    private String link; // Ruta relativa: "usuarios/36/perfil.jpg"

    /**
     * Constructor por defecto.
     */
    public Image() {
    }

    /**
     * Constructor con link.
     */
    public Image(String link) {
        this.link = link;
    }

    // ==================== GETTERS Y SETTERS ====================

    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Obtiene la extensión del archivo desde el link.
     * Ejemplo: "usuarios/36/perfil.jpg" → "jpg"
     */
    public String getExtension() {
        if (link == null || !link.contains("."))
            return "";
        return link.substring(link.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Obtiene el nombre del archivo desde el link.
     * Ejemplo: "usuarios/36/perfil.jpg" → "perfil.jpg"
     */
    public String getNombreArchivo() {
        if (link == null)
            return "";
        int ultimaBarra = Math.max(link.lastIndexOf('/'), link.lastIndexOf('\\'));
        return ultimaBarra >= 0 ? link.substring(ultimaBarra + 1) : link;
    }

    // ==================== EQUALS, HASHCODE, TOSTRING ====================

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Image image = (Image) o;
        return idImage != null && idImage.equals(image.idImage);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Image{" +
                "idImage=" + idImage +
                ", link='" + link + '\'' +
                '}';
    }
}
