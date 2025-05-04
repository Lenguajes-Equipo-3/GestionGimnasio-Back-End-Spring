package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.ImagenEjercicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EjercicioDataTest {

    @Autowired
    private EjercicioData ejercicioData;

    @Test
    @Sql(scripts = "/clean_database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void save_ejercicio_test() {
        // Arrange: Crear un ejercicio con datos de prueba
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdCategoriaEjercicio(1);
        ejercicio.setNombreEjercicio("Ejercicio de prueba");
        ejercicio.setDescripcionEjercicio("Descripción de prueba");
        ejercicio.setCodigoEquipo("EQ123");

        ImagenEjercicio imagen1 = new ImagenEjercicio();
        imagen1.setUrlImagen("http://example.com/imagen1.jpg");
        imagen1.setDescripcionImagen("Imagen de prueba 1");

        ImagenEjercicio imagen2 = new ImagenEjercicio();
        imagen2.setUrlImagen("http://example.com/imagen2.jpg");
        imagen2.setDescripcionImagen("Imagen de prueba 2");

        ejercicio.setImagenes(List.of(imagen1, imagen2));

        // Act: Guardar el ejercicio
        ejercicioData.save(ejercicio);

        // Assert: Verificar que el ID del ejercicio se haya generado
        assertNotNull(ejercicio.getIdEjercicio(), "El ID del ejercicio no debe ser nulo");
        assertNotEquals(0, ejercicio.getIdEjercicio(), "El ID del ejercicio debe ser mayor a 0");
    }

    @Test
    @Sql(scripts = "/clean_database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void update_ejercicio_test() {
        // Arrange: Crear y guardar un ejercicio inicial
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdCategoriaEjercicio(1);
        ejercicio.setNombreEjercicio("Ejercicio inicial");
        ejercicio.setDescripcionEjercicio("Descripción inicial");
        ejercicio.setCodigoEquipo("EQ001");

        ImagenEjercicio imagen = new ImagenEjercicio();
        imagen.setUrlImagen("http://example.com/imagen_inicial.jpg");
        imagen.setDescripcionImagen("Imagen inicial");

        ejercicio.setImagenes(List.of(imagen));
        ejercicioData.save(ejercicio);

        // Act: Actualizar los datos del ejercicio
        ejercicio.setNombreEjercicio("Ejercicio actualizado");
        ejercicio.setDescripcionEjercicio("Descripción actualizada");
        ejercicio.setCodigoEquipo("EQ002");

        ImagenEjercicio nuevaImagen = new ImagenEjercicio();
        nuevaImagen.setUrlImagen("http://example.com/imagen_actualizada.jpg");
        nuevaImagen.setDescripcionImagen("Imagen actualizada");

        ejercicio.setImagenes(List.of(nuevaImagen));
        ejercicioData.update(ejercicio);

        // Assert: Verificar que los datos se hayan actualizado correctamente
        Ejercicio ejercicioActualizado = ejercicioData.findById(ejercicio.getIdEjercicio());
        assertNotNull(ejercicioActualizado, "El ejercicio actualizado no debe ser nulo");
        assertEquals("Ejercicio actualizado", ejercicioActualizado.getNombreEjercicio());
        assertEquals("Descripción actualizada", ejercicioActualizado.getDescripcionEjercicio());
        assertEquals("EQ002", ejercicioActualizado.getCodigoEquipo());
        assertEquals(1, ejercicioActualizado.getImagenes().size());
        assertEquals("http://example.com/imagen_actualizada.jpg", ejercicioActualizado.getImagenes().get(0).getUrlImagen());
    }

    @Test
    @Sql(scripts = "/clean_database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_ejercicio_test() {
        // Arrange: Crear y guardar un ejercicio inicial
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdCategoriaEjercicio(1);
        ejercicio.setNombreEjercicio("Ejercicio a eliminar");
        ejercicio.setDescripcionEjercicio("Descripción a eliminar");
        ejercicio.setCodigoEquipo("EQ003");

        ImagenEjercicio imagen = new ImagenEjercicio();
        imagen.setUrlImagen("http://example.com/imagen_eliminar.jpg");
        imagen.setDescripcionImagen("Imagen a eliminar");

        ejercicio.setImagenes(List.of(imagen));
        ejercicioData.save(ejercicio);

        // Act: Eliminar el ejercicio
        ejercicioData.delete(ejercicio.getIdEjercicio());

        // Assert: Verificar que el ejercicio ya no exista
        Ejercicio ejercicioEliminado = ejercicioData.findById(ejercicio.getIdEjercicio());
        assertNull(ejercicioEliminado, "El ejercicio debería haber sido eliminado");
    }

    @Test
    @Sql(scripts = {"/clean_database.sql", "/save_ejercicio_con_imagenes.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void find_all_ejercicios_test() {
        // Act: Obtener todos los ejercicios
        List<Ejercicio> ejercicios = ejercicioData.findAll();

        // Assert: Verificar que se obtengan los ejercicios esperados
        assertNotNull(ejercicios, "La lista de ejercicios no debe ser nula");
        assertEquals(2, ejercicios.size(), "Debe haber 2 ejercicios en la base de datos");

        // Verificar los datos del primer ejercicio
        Ejercicio ejercicio1 = ejercicios.get(0);
        assertEquals(1, ejercicio1.getIdEjercicio());
        assertEquals("Ejercicio 1", ejercicio1.getNombreEjercicio());
        assertEquals("Descripción 1", ejercicio1.getDescripcionEjercicio());
        assertEquals("EQ001", ejercicio1.getCodigoEquipo());
        assertEquals(1, ejercicio1.getImagenes().size());
        assertEquals("http://example.com/imagen1.jpg", ejercicio1.getImagenes().get(0).getUrlImagen());

        // Verificar los datos del segundo ejercicio
        Ejercicio ejercicio2 = ejercicios.get(1);
        assertEquals(2, ejercicio2.getIdEjercicio());
        assertEquals("Ejercicio 2", ejercicio2.getNombreEjercicio());
        assertEquals("Descripción 2", ejercicio2.getDescripcionEjercicio());
        assertEquals("EQ002", ejercicio2.getCodigoEquipo());
        assertEquals(1, ejercicio2.getImagenes().size());
        assertEquals("http://example.com/imagen2.jpg", ejercicio2.getImagenes().get(0).getUrlImagen());
    }

    @Test
    @Sql(scripts = {"/clean_database.sql", "/save_ejercicio_con_imagenes.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void find_ejercicio_by_id_test() {
        // Act: Obtener el ejercicio con ID 1
        Ejercicio ejercicio = ejercicioData.findById(1);

        // Assert: Verificar que el ejercicio no sea nulo y que los datos sean correctos
        assertNotNull(ejercicio, "El ejercicio no debe ser nulo");
        assertEquals(1, ejercicio.getIdEjercicio(), "El ID del ejercicio debe ser 1");
        assertEquals(1, ejercicio.getIdCategoriaEjercicio(), "El ID de la categoría debe ser 1");
        assertEquals("Ejercicio 1", ejercicio.getNombreEjercicio(), "El nombre del ejercicio no coincide");
        assertEquals("Descripción 1", ejercicio.getDescripcionEjercicio(), "La descripción no coincide");
        assertEquals("EQ001", ejercicio.getCodigoEquipo(), "El código del equipo no coincide");

        // Verificar las imágenes asociadas
        assertNotNull(ejercicio.getImagenes(), "La lista de imágenes no debe ser nula");
        assertEquals(1, ejercicio.getImagenes().size(), "El ejercicio debe tener 1 imagen asociada");
        assertEquals("http://example.com/imagen1.jpg", ejercicio.getImagenes().get(0).getUrlImagen(), "La URL de la imagen no coincide");
        assertEquals("Imagen asociada al Ejercicio 1", ejercicio.getImagenes().get(0).getDescripcionImagen(), "La descripción de la imagen no coincide");
    }

}