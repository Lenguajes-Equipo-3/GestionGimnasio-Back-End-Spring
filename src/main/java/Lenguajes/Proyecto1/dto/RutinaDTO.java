package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class RutinaDTO {

    private int idRutina;

    @NotNull(message = "El empleado es obligatorio")
    private EmpleadoDTO empleado;

    @NotNull(message = "El cliente es obligatorio")
    private ClienteDTO cliente;

    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDate fechaCreacion;

    @NotNull(message = "La fecha de renovación es obligatoria")
    private LocalDate fechaRenovacion;

    @NotBlank(message = "El objetivo es obligatorio")
    @Size(max = 255, message = "El objetivo no puede tener más de 255 caracteres")
    private String objetivo;

    @Size(max = 255, message = "Las lesiones no pueden tener más de 255 caracteres")
    private String lesiones;

    @Size(max = 255, message = "Las enfermedades no pueden tener más de 255 caracteres")
    private String enfermedades;

    @NotNull(message = "El estado de vigencia es obligatorio")
    private Boolean esVigente;

    @NotNull(message = "La lista de ejercicios no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un ejercicio en la rutina")
    private List<ItemRutinaEjercicioDTO> ejercicios;

    @NotNull(message = "La lista de medidas no puede ser nula")
    private List<ItemRutinaMedidaDTO> medidas;

    // Getters y Setters
    public int getIdRutina() {
        return idRutina;
    }
    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }
    public EmpleadoDTO getEmpleado() {
        return empleado;
    }
    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }
    public ClienteDTO getCliente() {
        return cliente;
    }
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }
    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }
    public String getObjetivo() {
        return objetivo;
    }
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    public String getLesiones() {
        return lesiones;
    }
    public void setLesiones(String lesiones) {
        this.lesiones = lesiones;
    }
    public String getEnfermedades() {
        return enfermedades;
    }
    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }
    public Boolean getEsVigente() {
        return esVigente;
    }
    public void setEsVigente(Boolean esVigente) {
        this.esVigente = esVigente;
    }
    public List<ItemRutinaEjercicioDTO> getEjercicios() {
        return ejercicios;
    }
    public void setEjercicios(List<ItemRutinaEjercicioDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }
    public List<ItemRutinaMedidaDTO> getMedidas() {
        return medidas;
    }
    public void setMedidas(List<ItemRutinaMedidaDTO> medidas) {
        this.medidas = medidas;
    }

}