package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.CategoriaMedidaCorporalData;
import Lenguajes.Proyecto1.domain.CategoriaMedidaCorporal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaMedidaCorporalBusiness {

    @Autowired
    private CategoriaMedidaCorporalData categoriaMedidaCorporalData;

    // Guardar categoría con verificación de duplicados
    public void save(CategoriaMedidaCorporal categoriaMedidaCorporal) {
        // Verificar si el nombre de la categoría ya existe
        if (categoriaMedidaCorporalData.existsByName(categoriaMedidaCorporal.getNombreCategoria())) {
            throw new IllegalArgumentException("La categoría ya existe");
        }

        categoriaMedidaCorporalData.save(categoriaMedidaCorporal);
    }


    public CategoriaMedidaCorporal getCategoriaMedidaCorporalById(int idCategoria) {

        if (idCategoria <= 0) {
            throw new IllegalArgumentException("El ID de la categoria no es válido.");
        }
        // Obtener la categoría desde la base de datos
        return categoriaMedidaCorporalData.findById(idCategoria);
    }


    public List<CategoriaMedidaCorporal> getAllCategoriasMedidasCorporales() {
        return categoriaMedidaCorporalData.findAll();
    }


    public void updateCategoriaMedidaCorporal(CategoriaMedidaCorporal categoria) {
        System.out.println("ID de la categoría BUSII: " + categoria.getIdCategoria());

        if (categoria.getIdCategoria() <= 0) {
            throw new IllegalArgumentException("El ID de la categoria es inválido.");
        }
        // Verificar que el nombre no esté duplicado antes de actualizar
        if (categoriaMedidaCorporalData.existsByName(categoria.getNombreCategoria())) {
            throw new IllegalArgumentException("El nombre de la categoría ya existe.");
        }
        categoriaMedidaCorporalData.update(categoria);
    }

    public void delete(int idCategoria) {
        categoriaMedidaCorporalData.delete(idCategoria);
    }
}
