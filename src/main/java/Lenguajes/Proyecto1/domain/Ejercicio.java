package Lenguajes.Proyecto1.domain;


public class Ejercicio {
    private int idEjercicio;
    private int idCategoriaEjercicio;
    private String nombreEjercicio;
    private String descripcionEjercicio;
    private String codigoEquipo;

    public Ejercicio() {
    }

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
