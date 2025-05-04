package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.EjercicioData;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.ImagenEjercicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EjercicioBusinessTest {

    @Mock
    private EjercicioData ejercicioData;

    @InjectMocks
    private EjercicioBusiness ejercicioBusiness;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEjercicio() {
        // Arrange: Crear un ejercicio válido
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombreEjercicio("Prueba");
        ejercicio.setDescripcionEjercicio("Descripción de prueba");
        ejercicio.setCodigoEquipo("EQ123");

        ImagenEjercicio imagen = new ImagenEjercicio();
        imagen.setUrlImagen("http://example.com/imagen.jpg");
        imagen.setDescripcionImagen("Imagen de prueba");
        ejercicio.setImagenes(List.of(imagen));

        // Act
        ejercicioBusiness.saveEjercicio(ejercicio);

        // Assert
        verify(ejercicioData, times(1)).save(ejercicio);
    }

    @Test
    void testSaveEjercicioInvalidData() {
        // Arrange: Crear un ejercicio con datos inválidos
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombreEjercicio(""); // Nombre vacío
        ejercicio.setDescripcionEjercicio("Descripción de prueba");
        ejercicio.setCodigoEquipo("EQ123");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ejercicioBusiness.saveEjercicio(ejercicio));
    }

    @Test
    void testDeleteEjercicio() {
        // Act
        ejercicioBusiness.deleteEjercicio(1);

        // Assert
        verify(ejercicioData, times(1)).delete(1);
    }

    @Test
    void testGetEjercicioById() {
        // Arrange: Configurar un ejercicio de prueba
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(13);
        ejercicio.setNombreEjercicio("Ejercicio actualizado");
        ejercicio.setDescripcionEjercicio("Descripción actualizada");
        ejercicio.setCodigoEquipo("EQ002");

        ImagenEjercicio imagen = new ImagenEjercicio();
        imagen.setUrlImagen("http://example.com/imagen_actualizada.jpg");
        imagen.setDescripcionImagen("Imagen actualizada");
        ejercicio.setImagenes(List.of(imagen));

        when(ejercicioData.findById(13)).thenReturn(ejercicio);

        // Act
        Ejercicio result = ejercicioBusiness.getEjercicioById(13);

        // Assert
        assertNotNull(result);
        assertEquals(13, result.getIdEjercicio());
        assertEquals("Ejercicio actualizado", result.getNombreEjercicio());
        assertEquals("Descripción actualizada", result.getDescripcionEjercicio());
        assertEquals("EQ002", result.getCodigoEquipo());
        assertEquals(1, result.getImagenes().size());
        assertEquals("http://example.com/imagen_actualizada.jpg", result.getImagenes().get(0).getUrlImagen());
    }

    @Test
    void testUpdateEjercicio() {
        // Arrange: Crear un ejercicio válido
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(1);
        ejercicio.setNombreEjercicio("Prueba");
        ejercicio.setDescripcionEjercicio("Descripción de prueba");
        ejercicio.setCodigoEquipo("EQ123");

        ImagenEjercicio imagen = new ImagenEjercicio();
        imagen.setUrlImagen("http://example.com/imagen.jpg");
        imagen.setDescripcionImagen("Imagen de prueba");
        ejercicio.setImagenes(List.of(imagen));

        // Act
        ejercicioBusiness.updateEjercicio(ejercicio);

        // Assert
        verify(ejercicioData, times(1)).update(ejercicio);
    }
}