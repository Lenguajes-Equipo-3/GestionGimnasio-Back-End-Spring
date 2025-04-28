package Lenguajes.Proyecto1.domain;

import java.util.ArrayList;
import java.util.List;

public class CategoriaEjercicio {
    private int idCategoriaEjercicio;
    private String nombreCategoria;
    private String imagen;
    private List<Ejercicio> ejercicios;

    public CategoriaEjercicio() {
        this.ejercicios = new ArrayList<>();
    }

    public int getIdCategoria() {
        return idCategoriaEjercicio;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoriaEjercicio = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public void agregarEjercicio(Ejercicio ejercicio) {
        this.ejercicios.add(ejercicio);
    }

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
