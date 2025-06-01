package Lenguajes.Proyecto1.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rutina {
    private int idRutina;
    private Empleado empleado;
    private Cliente cliente;
    private LocalDate fechaCreacion;
    private LocalDate fechaRenovacion;
    private String objetivo;
    private String lesiones;
    private String enfermedades;
    private boolean esVigente;

    private List<ItemRutinaEjercicio> ejercicios;
    private List<ItemRutinaMedida> medidas;

    public Rutina() {
        this.idRutina = 0;
        this.empleado = new Empleado();
        this.cliente = new Cliente();
        this.fechaCreacion = LocalDate.now();
        this.fechaRenovacion = LocalDate.now();
        this.objetivo = "";
        this.lesiones = "";
        this.enfermedades = "";
        this.esVigente = true;
        this.ejercicios = new ArrayList<>();
        this.medidas = new ArrayList<>();
    }

    public Rutina(int idRutina, Empleado empleado, Cliente cliente, LocalDate fechaCreacion, LocalDate fechaRenovacion,
                  String objetivo, String lesiones, String enfermedades, boolean esVigente) {
        this.idRutina = idRutina;
        this.empleado = empleado;
        this.cliente = cliente;
        this.fechaCreacion = fechaCreacion;
        this.fechaRenovacion = fechaRenovacion;
        this.objetivo = objetivo;
        this.lesiones = lesiones;
        this.enfermedades = enfermedades;
        this.esVigente = esVigente;
    }

    public int getIdRutina() {
        return idRutina;
    }
    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }
    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
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
    public boolean isEsVigente() {
        return esVigente;
    }
    public void setEsVigente(boolean esVigente) {
        this.esVigente = esVigente;
    }
    public List<ItemRutinaEjercicio> getEjercicios() {
        return ejercicios;
    }
    public void setEjercicios(List<ItemRutinaEjercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
    public List<ItemRutinaMedida> getMedidas() {
        return medidas;
    }
    public void setMedidas(List<ItemRutinaMedida> medidas) {
        this.medidas = medidas;
    }

}
