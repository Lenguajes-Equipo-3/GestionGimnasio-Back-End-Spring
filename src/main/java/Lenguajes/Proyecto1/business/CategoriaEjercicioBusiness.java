package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.CategoriaEjercicioData;
import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaEjercicioBusiness {

    @Autowired
    private CategoriaEjercicioData categoriaEjercicioData;

    public void saveCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        // Validar los datos de la categoría
        if (categoriaEjercicio.getNombreCategoria() == null || categoriaEjercicio.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        if (categoriaEjercicio.getImagen() == null || categoriaEjercicio.getImagen().isEmpty()) {
            throw new IllegalArgumentException("La imagen de la categoría no puede estar vacía");
        }

        // Guardar la categoría en la base de datos
        categoriaEjercicioData.save(categoriaEjercicio);
    }

    public CategoriaEjercicio getCategoriaEjercicioById(int idCategoria) {
        // Validar el ID de la categoría
        if (idCategoria <= 0) {
            throw new IllegalArgumentException("El ID de la categoría no es válido");
        }

        // Obtener la categoría de la base de datos
        return categoriaEjercicioData.findById(idCategoria);
    }

    public void updateCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        // Validar los datos de la categoría
        if (categoriaEjercicio.getIdCategoria() <= 0) {
            throw new IllegalArgumentException("El ID de la categoría no es válido");
        }
        if (categoriaEjercicio.getNombreCategoria() == null || categoriaEjercicio.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        if (categoriaEjercicio.getImagen() == null || categoriaEjercicio.getImagen().isEmpty()) {
            throw new IllegalArgumentException("La imagen de la categoría no puede estar vacía");
        }

        // Actualizar la categoría en la base de datos
        categoriaEjercicioData.update(categoriaEjercicio);
    }

    public void deleteCategoriaEjercicio(int idCategoria) {
        // Validar el ID de la categoría
        if (idCategoria <= 0) {
            throw new IllegalArgumentException("El ID de la categoría no es válido");
        }

        // Eliminar la categoría de la base de datos
        categoriaEjercicioData.delete(idCategoria);
    }

    public List<CategoriaEjercicio> getAllCategoriaEjercicios() {
        // Obtener todas las categorías de la base de datos
        return categoriaEjercicioData.findAll();
    }

}
