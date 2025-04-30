package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EjercicioDTO {

    @NotNull(message = "El ID del ejercicio no puede ser nulo")
    private int idEjercicio;

    @NotNull(message = "El ID de la categoría del ejercicio no puede ser nulo")
    private int idCategoriaEjercicio;

    @NotBlank(message = "El nombre del ejercicio no puede estar vacío")
    @Size(max = 50, message = "El nombre del ejercicio no puede tener más de 50 caracteres")
    private String nombreEjercicio;

    @NotBlank(message = "La descripción del ejercicio no puede estar vacía")
    @Size(max = 100, message = "La descripción del ejercicio no puede tener más de 100 caracteres")
    private String descripcionEjercicio;

    @Size(max = 20, message = "El código del equipo no puede tener más de 20 caracteres")
    private String codigoEquipo;

    // Getters y Setters
    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getIdCategoriaEjercicio() {
        return idCategoriaEjercicio;
    }

    public void setIdCategoriaEjercicio(int idCategoriaEjercicio) {
        this.idCategoriaEjercicio = idCategoriaEjercicio;
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
}