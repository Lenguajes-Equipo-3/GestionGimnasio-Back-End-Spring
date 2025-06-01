package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.*;

public class ItemRutinaEjercicioDTO {
    private RutinaDTO rutina;

    @NotNull(message = "El ejercicio es obligatorio")
    private EjercicioDTO ejercicio;

    @Min(value = 1, message = "Las series deben ser al menos 1")
    private int series;

    @Min(value = 1, message = "Las repeticiones deben ser al menos 1")
    private int repeticiones;

    @Size(max = 50, message = "El código del equipo no puede tener más de 50 caracteres")
    private String codigoEquipo;

    public RutinaDTO getRutina() {
        return rutina;
    }
    public void setRutina(RutinaDTO rutina) {
        this.rutina = rutina;
    }
    public EjercicioDTO getEjercicio() {
        return ejercicio;
    }
    public void setEjercicio(EjercicioDTO ejercicio) {
        this.ejercicio = ejercicio;
    }
    public int getSeries() {
        return series;
    }
    public void setSeries(int series) {
        this.series = series;
    }
    public int getRepeticiones() {
        return repeticiones;
    }
    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
    public String getCodigoEquipo() {
        return codigoEquipo;
    }
    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }
}