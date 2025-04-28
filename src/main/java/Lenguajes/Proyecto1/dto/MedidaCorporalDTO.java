package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MedidaCorporalDTO {

    @NotNull(message = "El ID de la medida corporal no puede ser nulo")
    private int idMedida;

    @NotBlank(message = "El nombre de la medida no puede estar vacío")
    @Size(max = 50, message = "El nombre de la medida no puede tener más de 50 caracteres")
    private String nombreMedida;

    @NotBlank(message = "La unidad de medida no puede estar vacía")
    @Size(max = 50, message = "La unidad de medida no puede tener más de 50 caracteres")
    private String unidadMedida;

    @NotNull(message = "El campo 'esObligatoria' no puede ser nulo")
    private boolean esObligatoria;

    @NotNull(message = "El ID de la categoría de la medida corporal no puede ser nulo")
    private int idCategoria;

    // Getters y Setters
    public int getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(int idMedida) {
        this.idMedida = idMedida;
    }

    public String getNombreMedida() {
        return nombreMedida;
    }

    public void setNombreMedida(String nombreMedida) {
        this.nombreMedida = nombreMedida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public boolean getEsObligatoria() {
        return esObligatoria;
    }

    public void setEsObligatoria(boolean esObligatoria) {
        this.esObligatoria = esObligatoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
