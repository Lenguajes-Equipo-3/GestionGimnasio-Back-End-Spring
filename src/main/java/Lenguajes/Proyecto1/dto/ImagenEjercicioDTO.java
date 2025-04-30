package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ImagenEjercicioDTO {

    private int idImagen;

    @NotBlank(message = "La URL de la imagen no puede estar vacía")
    @Size(max = 255, message = "La URL de la imagen no puede tener más de 255 caracteres")
    private String urlImagen;

    @Size(max = 255, message = "La descripción de la imagen no puede tener más de 255 caracteres")
    private String descripcionImagen;

    // Getters y Setters
    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcionImagen() {
        return descripcionImagen;
    }

    public void setDescripcionImagen(String descripcionImagen) {
        this.descripcionImagen = descripcionImagen;
    }
}
