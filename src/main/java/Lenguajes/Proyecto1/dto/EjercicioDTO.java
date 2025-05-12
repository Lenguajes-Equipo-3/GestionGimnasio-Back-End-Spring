package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class EjercicioDTO {

    private int idEjercicio;

    @NotNull(message = "La categoría del ejercicio no puede ser nula")
    private CategoriaEjercicioDTO categoriaEjercicio;

    @NotBlank(message = "El nombre del ejercicio no puede estar vacío")
    @Size(max = 100, message = "El nombre del ejercicio no puede tener más de 100 caracteres")
    private String nombreEjercicio;

    @NotBlank(message = "La descripción del ejercicio no puede estar vacía")
    @Size(max = 500, message = "La descripción del ejercicio no puede tener más de 500 caracteres")
    private String descripcionEjercicio;

    @NotBlank(message = "El código del equipo no puede estar vacío")
    @Size(max = 50, message = "El código del equipo no puede tener más de 50 caracteres")
    private String codigoEquipo;

    @NotNull(message = "La lista de imágenes no puede ser nula")
    private List<ImagenEjercicioDTO> imagenes;

    // Getters y Setters
    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public CategoriaEjercicioDTO getCategoriaEjercicio() {
        return categoriaEjercicio;
    }

    public void setCategoriaEjercicio(CategoriaEjercicioDTO categoriaEjercicio) {
        this.categoriaEjercicio = categoriaEjercicio;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public String getDescripcionEjercicio() {
        return descripcionEjercicio;
    }

    public void setDescripcionEjercicio(String descripcionEjercicio) {
        this.descripcionEjercicio = descripcionEjercicio;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public List<ImagenEjercicioDTO> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenEjercicioDTO> imagenes) {
        this.imagenes = imagenes;
    }
}