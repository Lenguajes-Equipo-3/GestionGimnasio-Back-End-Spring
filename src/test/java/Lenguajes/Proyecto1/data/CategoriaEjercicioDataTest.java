package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoriaEjercicioDataTest {

    @Autowired
    private CategoriaEjercicioData categoriaEjercicioData;

    @Test
    @Sql(scripts = "/limpiar_tabla_categoria.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void save_categoria_test() {
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setNombreCategoria("Resistencia");
        categoria.setImagen("http://example.com/resistencia.jpg");

        categoriaEjercicioData.save(categoria);

        List<CategoriaEjercicio> categorias = categoriaEjercicioData.findAll();
        assertTrue(categorias.stream().anyMatch(c -> c.getNombreCategoria().equals("Resistencia")));
    }

    @Test
    @Sql(scripts = {"/limpiar_tabla_categoria.sql", "/insertar_categorias.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void find_by_id_test() {
        CategoriaEjercicio categoria = categoriaEjercicioData.findById(1);

        assertNotNull(categoria);
        assertEquals(1, categoria.getIdCategoria());
        assertEquals("Cardio", categoria.getNombreCategoria());
        assertEquals("http://example.com/cardio.jpg", categoria.getImagen());
    }

    @Test
    @Sql(scripts = {"/limpiar_tabla_categoria.sql", "/insertar_categorias.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void find_all_test() {
        List<CategoriaEjercicio> categorias = categoriaEjercicioData.findAll();

        assertNotNull(categorias);
        assertEquals(2, categorias.size());
    }

    @Test
    @Sql(scripts = {"/limpiar_tabla_categoria.sql", "/insertar_categorias.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void update_categoria_test() {
        CategoriaEjercicio categoria = categoriaEjercicioData.findById(1);
        categoria.setNombreCategoria("Cardio Extremo");
        categoria.setImagen("http://example.com/cardio_extremo.jpg");

        categoriaEjercicioData.update(categoria);

        CategoriaEjercicio actualizada = categoriaEjercicioData.findById(1);
        assertEquals("Cardio Extremo", actualizada.getNombreCategoria());
        assertEquals("http://example.com/cardio_extremo.jpg", actualizada.getImagen());
    }

    @Test
    @Sql(scripts = {"/limpiar_tabla_categoria.sql", "/insertar_categorias.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_categoria_test() {
        categoriaEjercicioData.delete(2);

        List<CategoriaEjercicio> categorias = categoriaEjercicioData.findAll();
        assertEquals(1, categorias.size());
        assertFalse(categorias.stream().anyMatch(c -> c.getIdCategoria() == 2));
    }
}
