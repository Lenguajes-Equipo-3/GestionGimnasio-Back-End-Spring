package Lenguajes.Proyecto1.domain;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio {
    private int idEjercicio;
    private CategoriaEjercicio categoriaEjercicio;
    private String nombreEjercicio;
    private String descripcionEjercicio;
    private String codigoEquipo;
    private List<ImagenEjercicio> imagenes;

    public Ejercicio() {
        this.idEjercicio = 0;
        this.categoriaEjercicio = new CategoriaEjercicio();
        this.nombreEjercicio = "";
        this.descripcionEjercicio = "";
        this.codigoEquipo = "";
        this.imagenes = new ArrayList<>();

    }

    public Ejercicio(int idEjercicio, CategoriaEjercicio categoriaEjercicio, String nombreEjercicio,
                     String descripcionEjercicio, String codigoEquipo, List<ImagenEjercicio> imagenes) {
        this.idEjercicio = idEjercicio;
        this.categoriaEjercicio = categoriaEjercicio;
        this.nombreEjercicio = nombreEjercicio;
        this.descripcionEjercicio = descripcionEjercicio;
        this.codigoEquipo = codigoEquipo;
        this.imagenes = imagenes;
    }

    // Getters y Setters
    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public CategoriaEjercicio getCategoriaEjercicio() {
        return categoriaEjercicio;
    }

    public void setCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
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

    public List<ImagenEjercicio> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenEjercicio> imagenes) {
        this.imagenes = imagenes;
    }
}