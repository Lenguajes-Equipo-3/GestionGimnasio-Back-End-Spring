package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoriaEjercicioDTO {

    @NotNull(message = "El ID de la categoría no puede ser nulo")
    private int idCategoriaEjercicio ;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Size(max = 50, message = "El nombre de la categoría no puede tener más de 50 caracteres")
    private String nombreCategoria;

    @Size(max = 255, message = "La imagen no puede tener más de 255 caracteres")
    private String imagen;

    // Getters y Setters
    public int getIdCategoriaEjercicio() {
        return idCategoriaEjercicio;
    }

    public void setIdCategoriaEjercicio(int idCategoria) {
        this.idCategoriaEjercicio = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
