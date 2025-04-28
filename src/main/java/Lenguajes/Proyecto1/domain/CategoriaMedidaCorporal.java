package Lenguajes.Proyecto1.domain;

import java.util.ArrayList;
import java.util.List;

public class CategoriaMedidaCorporal {
    private int idCategoria;
    private String nombreCategoria;
    private List<MedidaCorporal> medidas;

    public CategoriaMedidaCorporal() {
        this.medidas = new ArrayList<>();
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public List<MedidaCorporal> getMedidas() {
        return medidas;
    }

    public void setMedidas(List<MedidaCorporal> medidas) {
        this.medidas = medidas;
    }

    public void agregarMedida(MedidaCorporal medida) {
        this.medidas.add(medida);
    }
}
