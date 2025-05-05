package Lenguajes.Proyecto1.data;


import Lenguajes.Proyecto1.domain.CategoriaMedidaCorporal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CategoriaMedidaCorporalDataTest {

    @Autowired
    private CategoriaMedidaCorporalData categoriaMedidaData;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void SaveCategoriaMedida() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setNombreCategoria("Abdomen");

        // Act
        categoriaMedidaData.save(categoria);
        int idCategoria = categoriaMedidaData.findIdByNombre(categoria.getNombreCategoria());
        // Assert
        List<CategoriaMedidaCorporal> categorias = categoriaMedidaData.findAll();
        boolean encontrada = categorias.stream()
                .anyMatch(c -> c.getNombreCategoria().equals("Abdomen"));

        assertTrue(encontrada, "La categoría guardada debería estar en la lista");
        // Cleanup
        jdbcTemplate.update("DELETE FROM CategoriaMedida WHERE id_categoria = ?", idCategoria);

    }

    @Test
    void findById_CategoriaExistente() {
        // Arrange: esta categoría exista en tu base de datos
        int idCategoriaExistente = 1;

        // Act
        CategoriaMedidaCorporal categoria = categoriaMedidaData.findById(idCategoriaExistente);

        // Assert
        assertNotNull(categoria);
        assertEquals(idCategoriaExistente, categoria.getIdCategoria());
        assertNotNull(categoria.getNombreCategoria());
    }

    @Test
    void findById_CategoriaInexistente() {
        // Arrange: un ID que no exista
        int idInexistente = 9999;

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoriaMedidaData.findById(idInexistente);
        });

        assertTrue(exception.getMessage().contains("Categoría no encontrada"));
    }

    @Test
    void findAllCategorias() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setNombreCategoria("Abdomen");
        categoriaMedidaData.save(categoria);
        int idCategoria = categoriaMedidaData.findIdByNombre(categoria.getNombreCategoria());

        // Act: Llamar al método findAll para obtener todas las categorías
        List<CategoriaMedidaCorporal> categorias = categoriaMedidaData.findAll();

        // Assert: Verificar que las categorías se hayan recuperado correctamente
        assertTrue(categorias.stream().anyMatch(c -> c.getNombreCategoria().equals("Abdomen")));
        // Cleanup
        jdbcTemplate.update("DELETE FROM CategoriaMedida WHERE id_categoria = ?", idCategoria);
    }

    @Test
    void deleteCategoria() {
        // Arrange: Crear y guardar una categoría
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setNombreCategoria("Brazos");
        categoriaMedidaData.save(categoria);

        // Verificar que la categoría se haya guardado correctamente
        List<CategoriaMedidaCorporal> categoriasAntesDeEliminar = categoriaMedidaData.findAll();
        assertTrue(categoriasAntesDeEliminar.stream().anyMatch(c -> c.getNombreCategoria().equals("Brazos")));

        // Act: Llamar al método delete para eliminar la categoría
        int idCategoria = categoriaMedidaData.findIdByNombre(categoria.getNombreCategoria());
        categoriaMedidaData.delete(idCategoria);

        // Assert: Verificar que la categoría ha sido eliminada
        List<CategoriaMedidaCorporal> categoriasDespuesDeEliminar = categoriaMedidaData.findAll();
        boolean encontrada = categoriasDespuesDeEliminar.stream()
                .anyMatch(c -> c.getNombreCategoria().equals("Brazos"));
        assertFalse(encontrada, "La categoría 'Brazos' debería haber sido eliminada.");
    }
    @Test
    void FindIdByNombre() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setNombreCategoria("Pecho");
        categoriaMedidaData.save(categoria);

        // Act
        Integer id = categoriaMedidaData.findIdByNombre("Pecho");

        // Assert
        assertNotNull(id, "El ID no debería ser nulo para una categoría existente");

        // Cleanup
        jdbcTemplate.update("DELETE FROM CategoriaMedida WHERE id_categoria = ?", id);
    }

    @Test
    void updateCategoriaMedida() {
        // Arrange: crear una nueva categoría
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setNombreCategoria("Abdomen");

        categoriaMedidaData.save(categoria);

        // Obtener el ID de la categoría guardada
        int idCategoria = categoriaMedidaData.findIdByNombre(categoria.getNombreCategoria());
        assertTrue(idCategoria > 0, "El ID de la categoría debería ser mayor que 0");

        // Act: actualizar la categoría
        categoria.setNombreCategoria("Cintura");
        categoria.setIdCategoria(idCategoria);
        categoriaMedidaData.update(categoria);

        // Assert: verificar que el nombre se haya actualizado
        CategoriaMedidaCorporal categoriaActualizada = categoriaMedidaData.findById(idCategoria);
        assertNotNull(categoriaActualizada, "La categoría debería ser encontrada");
        assertEquals("Cintura", categoriaActualizada.getNombreCategoria(), "El nombre de la categoría debería haberse actualizado");

        // Cleanup:
        jdbcTemplate.update("DELETE FROM CategoriaMedida WHERE id_categoria = ?", idCategoria);
    }


}//end
