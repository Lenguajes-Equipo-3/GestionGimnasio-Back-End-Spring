package Lenguajes.Proyecto1.domain;

import java.util.List;

public class Ejercicio {
    private int idEjercicio;
    private CategoriaEjercicio categoriaEjercicio;
    private String nombreEjercicio;
    private String descripcionEjercicio;
    private String codigoEquipo;
    private List<ImagenEjercicio> imagenes;

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

	@Override
	public String toString() {
		return "Ejercicio [idEjercicio=" + idEjercicio + ", categoriaEjercicio=" + categoriaEjercicio
				+ ", nombreEjercicio=" + nombreEjercicio + ", descripcionEjercicio=" + descripcionEjercicio
				+ ", codigoEquipo=" + codigoEquipo + ", imagenes=" + imagenes + "]";
	}
    
    
    
}