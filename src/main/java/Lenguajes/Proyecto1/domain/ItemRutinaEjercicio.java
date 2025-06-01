package Lenguajes.Proyecto1.domain;

public class ItemRutinaEjercicio {
    private Rutina rutina;
    private Ejercicio ejercicio;
    private int series;
    private int repeticiones;
    private String codigoEquipo;

    public ItemRutinaEjercicio() {
        this.rutina = null;
        this.ejercicio = null;
        this.series = 0;
        this.repeticiones = 0;
        this.codigoEquipo = "";
    }

    public ItemRutinaEjercicio(Rutina rutina, Ejercicio ejercicio, int series, int repeticiones, String codigoEquipo) {
        this.rutina = rutina;
        this.ejercicio = ejercicio;
        this.series = series;
        this.repeticiones = repeticiones;
        this.codigoEquipo = codigoEquipo;
    }

    public Rutina getRutina() {
        return rutina;
    }
    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }
    public Ejercicio getEjercicio() {
        return ejercicio;
    }
    public void setEjercicio(Ejercicio ejercicio) {
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
