package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.CategoriaEjercicioData;
import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaEjercicioBusinessTest {

    @Mock
    private CategoriaEjercicioData categoriaEjercicioData;

    @InjectMocks
    private CategoriaEjercicioBusiness categoriaBusiness;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategoriaEjercicio_ValidData() {
        // Arrange
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setNombreCategoria("Cardio");
        categoria.setImagen("http://imagen.jpg");

        // Act
        categoriaBusiness.saveCategoriaEjercicio(categoria);

        // Assert
        verify(categoriaEjercicioData, times(1)).save(categoria);
    }

    @Test
    void testSaveCategoriaEjercicio_EmptyNombre() {
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setNombreCategoria("");  // Nombre vacío
        categoria.setImagen("http://imagen.jpg");

        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.saveCategoriaEjercicio(categoria));
    }

    @Test
    void testSaveCategoriaEjercicio_EmptyImagen() {
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setNombreCategoria("Fuerza");
        categoria.setImagen("");  // Imagen vacía

        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.saveCategoriaEjercicio(categoria));
    }

    @Test
    void testGetCategoriaEjercicioById_Valid() {
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setIdCategoria(1);
        categoria.setNombreCategoria("Estiramiento");
        categoria.setImagen("http://imagen.jpg");

        when(categoriaEjercicioData.findById(1)).thenReturn(categoria);

        CategoriaEjercicio result = categoriaBusiness.getCategoriaEjercicioById(1);

        assertNotNull(result);
        assertEquals("Estiramiento", result.getNombreCategoria());
    }

    @Test
    void testGetCategoriaEjercicioById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.getCategoriaEjercicioById(0));
    }

    @Test
    void testUpdateCategoriaEjercicio_Valid() {
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setIdCategoria(2);
        categoria.setNombreCategoria("Balance");
        categoria.setImagen("http://imagen.jpg");

        categoriaBusiness.updateCategoriaEjercicio(categoria);

        verify(categoriaEjercicioData, times(1)).update(categoria);
    }

    @Test
    void testUpdateCategoriaEjercicio_InvalidNombre() {
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setIdCategoria(1);
        categoria.setNombreCategoria("");  // Nombre inválido
        categoria.setImagen("http://imagen.jpg");

        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.updateCategoriaEjercicio(categoria));
    }

    @Test
    void testDeleteCategoriaEjercicio_Valid() {
        categoriaBusiness.deleteCategoriaEjercicio(1);
        verify(categoriaEjercicioData, times(1)).delete(1);
    }

    @Test
    void testDeleteCategoriaEjercicio_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.deleteCategoriaEjercicio(0));
    }

    @Test
    void testGetAllCategoriaEjercicios() {
        List<CategoriaEjercicio> mockList = List.of(new CategoriaEjercicio(), new CategoriaEjercicio());

        when(categoriaEjercicioData.findAll()).thenReturn(mockList);

        List<CategoriaEjercicio> result = categoriaBusiness.getAllCategoriaEjercicios();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
