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
        ejercicio.setIdEjercicio(1);
        ejercicio.setNombreEjercicio("Ejercicio 1");
        ejercicio.setDescripcionEjercicio("Descripción 1");
        ejercicio.setCodigoEquipo("EQ001");

        ImagenEjercicio imagen = new ImagenEjercicio();
        imagen.setUrlImagen("http://example.com/imagen.jpg");
        imagen.setDescripcionImagen("Imagen actualizada");
        ejercicio.setImagenes(List.of(imagen));

        when(ejercicioData.findById(1)).thenReturn(ejercicio);

        // Act
        Ejercicio result = ejercicioBusiness.getEjercicioById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdEjercicio());
        assertEquals("Ejercicio 1", result.getNombreEjercicio());
        assertEquals("Descripción 1", result.getDescripcionEjercicio());
        assertEquals("EQ001", result.getCodigoEquipo());
        assertEquals(1, result.getImagenes().size());
        assertEquals("http://example.com/imagen.jpg", result.getImagenes().get(0).getUrlImagen());
    }

    @Test
    void testGetEjercicioByName() {
        // Arrange: Configurar un ejercicio de prueba
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(1);
        ejercicio.setNombreEjercicio("Ejercicio 1");
        ejercicio.setDescripcionEjercicio("Descripción 1");
        ejercicio.setCodigoEquipo("EQ001");

        ImagenEjercicio imagen = new ImagenEjercicio();
        imagen.setUrlImagen("http://example.com/imagen.jpg");
        imagen.setDescripcionImagen("Imagen 1");
        ejercicio.setImagenes(List.of(imagen));

        when(ejercicioData.findByName("Ejercicio 1")).thenReturn(ejercicio);

        // Act
        Ejercicio result = ejercicioBusiness.getEjercicioByName("Ejercicio 1");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdEjercicio());
        assertEquals("Ejercicio 1", result.getNombreEjercicio());
        assertEquals("Descripción 1", result.getDescripcionEjercicio());
        assertEquals("EQ001", result.getCodigoEquipo());
        assertEquals(1, result.getImagenes().size());
        assertEquals("http://example.com/imagen.jpg", result.getImagenes().get(0).getUrlImagen());
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