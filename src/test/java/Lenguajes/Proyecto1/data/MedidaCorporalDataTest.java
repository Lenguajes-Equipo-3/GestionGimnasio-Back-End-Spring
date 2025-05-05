package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.MedidaCorporal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class MedidaCorporalDataTest {

    @Autowired
    private MedidaCorporalData medidaCorporalData;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Test
    void saveMedidaCorporal() throws Exception {
        // Arrange
        MedidaCorporal medidaCorporal = new MedidaCorporal();
        medidaCorporal.setNombreMedida("Talla");
        medidaCorporal.setEsObligatoria(true);
        medidaCorporal.setUnidadMedida("cm");
        medidaCorporal.setIdCategoria(1); // Asegúrate de que esta categoría exista en la BD

        // Act
        MedidaCorporal saved = medidaCorporalData.save(medidaCorporal);

        // Assert
        assertNotNull(saved.getIdMedida());
        assertTrue(saved.getIdMedida() > 0);
        assertEquals("Talla", saved.getNombreMedida());
        assertEquals("cm", saved.getUnidadMedida());
        assertTrue(saved.getEsObligatoria());
        assertEquals(1, saved.getIdCategoria());
      // Cleanup
        jdbcTemplate.update("DELETE FROM MedidaCorporal WHERE id_medida = ?", saved.getIdMedida());

    }

    @Test
    void updateMedidaCorporal() throws Exception {
        // Arrange: Crear y guardar una medida primero
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("Cintura");
        medida.setUnidadMedida("cm");
        medida.setEsObligatoria(true);
        medida.setIdCategoria(1);

        medida = medidaCorporalData.save(medida);

        // Act: Modificar algunos valores
        medida.setNombreMedida("Cintura Actualizada");
        medida.setUnidadMedida("mm");
        medida.setEsObligatoria(false);

        medidaCorporalData.update(medida);

        // Assert: Verificar que los cambios se aplicaron
        MedidaCorporal updated = medidaCorporalData.findById(medida.getIdMedida());
        assertNotNull(updated);
        assertEquals("Cintura Actualizada", updated.getNombreMedida());
        assertEquals("mm", updated.getUnidadMedida());
        assertFalse(updated.getEsObligatoria());
        // Cleanup
        jdbcTemplate.update("DELETE FROM MedidaCorporal WHERE id_medida = ?", medida.getIdMedida());
    }
    @Test
    void testFindById_Exists() throws Exception {
        // Arrange: insertamos una medida primero
        MedidaCorporal medidaInsertada = new MedidaCorporal();
        medidaInsertada.setNombreMedida("Cintura");
        medidaInsertada.setUnidadMedida("cm");
        medidaInsertada.setEsObligatoria(false);
        // Asegurarse de que esta categoría exista
        medidaInsertada.setIdCategoria(1);

        MedidaCorporal medidaGuardada = medidaCorporalData.save(medidaInsertada);

        // Act: buscamos por el ID
        MedidaCorporal medidaEncontrada = medidaCorporalData.findById(medidaGuardada.getIdMedida());

        // Assert: verificamos que los datos coincidan
        assertNotNull(medidaEncontrada);
        assertEquals(medidaGuardada.getIdMedida(), medidaEncontrada.getIdMedida());
        assertEquals("Cintura", medidaEncontrada.getNombreMedida());
        assertEquals("cm", medidaEncontrada.getUnidadMedida());
        assertFalse(medidaEncontrada.getEsObligatoria());
        assertEquals(1, medidaEncontrada.getIdCategoria());

        // Cleanup: eliminamos la medida de la base de datos
        jdbcTemplate.update("DELETE FROM MedidaCorporal WHERE id_medida = ?", medidaGuardada.getIdMedida());
    }

    @Test
    void testFindById_NotExists() {
        // Act: buscamos un ID que sabemos no existe
        MedidaCorporal medidaNoEncontrada = medidaCorporalData.findById(999999);

        // Assert: debería ser null
        assertNull(medidaNoEncontrada);
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange: Insertamos una medida de prueba
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("Espalda");
        medida.setUnidadMedida("cm");
        medida.setEsObligatoria(true);
        medida.setIdCategoria(1);

        MedidaCorporal medidaGuardada = medidaCorporalData.save(medida);

        // Act: Obtenemos todas las medidas
        List<MedidaCorporal> medidas = medidaCorporalData.findAll();

        // Assert: Verificamos que la lista no esté vacía y contenga la medida que insertamos
        assertNotNull(medidas);
        assertFalse(medidas.isEmpty());

        boolean encontrada = medidas.stream()
                .anyMatch(m -> m.getIdMedida() == medidaGuardada.getIdMedida()
                        && m.getNombreMedida().equals("Espalda")
                        && m.getUnidadMedida().equals("cm"));

        assertTrue(encontrada, "La medida insertada no fue encontrada en la lista.");

        // Cleanup
        jdbcTemplate.update("DELETE FROM MedidaCorporal WHERE id_medida = ?", medidaGuardada.getIdMedida());
    }

    @Test
    void testDeleteMedidaCorporal() throws Exception {
        // Arrange: Insertar una medida de prueba
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("Brazo derecho");
        medida.setUnidadMedida("cm");
        medida.setEsObligatoria(false);
        medida.setIdCategoria(1);

        MedidaCorporal medidaGuardada = medidaCorporalData.save(medida);

        // Confirmamos que fue guardada correctamente
        MedidaCorporal encontrada = medidaCorporalData.findById(medidaGuardada.getIdMedida());
        assertNotNull(encontrada, "La medida debería existir antes de eliminarla");

        // Act: Eliminar la medida
        medidaCorporalData.delete(medidaGuardada.getIdMedida());

        // Assert: Confirmar que ya no existe
        MedidaCorporal despuesDeEliminar = medidaCorporalData.findById(medidaGuardada.getIdMedida());
        assertNull(despuesDeEliminar, "La medida debería haber sido eliminada");
    }

    @Test
    void testDeleteMedidaInexistente() {
        // Arrange: ID que no existe en la base
        int idInexistente = 999999;

        assertDoesNotThrow(() -> medidaCorporalData.delete(idInexistente),
                "Eliminar un ID inexistente no debería lanzar excepción");
    }
}// end

