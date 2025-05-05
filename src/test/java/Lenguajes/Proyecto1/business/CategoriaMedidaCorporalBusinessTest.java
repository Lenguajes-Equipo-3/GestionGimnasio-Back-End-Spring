package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.CategoriaMedidaCorporalData;
import Lenguajes.Proyecto1.domain.CategoriaMedidaCorporal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaMedidaCorporalBusinessTest {
     @Mock
    private CategoriaMedidaCorporalData categoriaData;
     @InjectMocks
    private CategoriaMedidaCorporalBusiness categoriaBusiness;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void SaveCategoriaMedida() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setNombreCategoria("Abdomen");

        // Simulamos que la categoría no existe
        when(categoriaData.existsByName("Abdomen")).thenReturn(false);

        // Act
        categoriaBusiness.save(categoria);

        // Assert
        verify(categoriaData, times(1)).save(categoria);
    }
    @Test
    void SaveCategoriaMedida_ThrowsException_WhenCategoryExists() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setNombreCategoria("Abdomen");

        // Simulamos que la categoría ya existe
        when(categoriaData.existsByName("Abdomen")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.save(categoria),
                "La categoría ya existe");

        // Verificamos que el método save no se haya llamado
        verify(categoriaData, never()).save(categoria);
    }
    @Test
    void getCategoriaMedidaCorporalById_ValidId_ReturnsCategoria() {
        // Arrange
        int id = 1;
        CategoriaMedidaCorporal categoriaMock = new CategoriaMedidaCorporal();
        categoriaMock.setIdCategoria(id);
        categoriaMock.setNombreCategoria("Piernas");

        when(categoriaData.findById(id)).thenReturn(categoriaMock);

        // Act
        CategoriaMedidaCorporal result = categoriaBusiness.getCategoriaMedidaCorporalById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getIdCategoria());
        assertEquals("Piernas", result.getNombreCategoria());
        verify(categoriaData, times(1)).findById(id);
    }

    @Test
    void getCategoriaMedidaCorporalById_InvalidId_ThrowsException() {
        // Arrange
        int invalidId = 0;

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> categoriaBusiness.getCategoriaMedidaCorporalById(invalidId)
        );

        assertEquals("El ID de la categoria no es válido.", thrown.getMessage());
        verify(categoriaData, never()).findById(anyInt());
    }
    @Test
    void getAllCategoriasMedidasCorporales_ReturnsCategoriaList() {
        // Arrange
        CategoriaMedidaCorporal cat1 = new CategoriaMedidaCorporal();
        cat1.setIdCategoria(1);
        cat1.setNombreCategoria("Abdomen");

        CategoriaMedidaCorporal cat2 = new CategoriaMedidaCorporal();
        cat2.setIdCategoria(2);
        cat2.setNombreCategoria("Piernas");

        List<CategoriaMedidaCorporal> mockCategorias = List.of(cat1, cat2);

        when(categoriaData.findAll()).thenReturn(mockCategorias);

        // Act
        List<CategoriaMedidaCorporal> result = categoriaBusiness.getAllCategoriasMedidasCorporales();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Abdomen", result.get(0).getNombreCategoria());
        assertEquals("Piernas", result.get(1).getNombreCategoria());
        verify(categoriaData, times(1)).findAll();
    }
    @Test
    void deleteCategoriaMedida_DeletesCorrectly() {
        // Arrange
        int idCategoria = 1;

        // Act
        categoriaBusiness.delete(idCategoria);

        // Assert
        verify(categoriaData, times(1)).delete(idCategoria);
    }
    @Test
    void updateCategoriaMedidaCorporal_ValidData_UpdatesSuccessfully() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setIdCategoria(1);
        categoria.setNombreCategoria("Piernas");

        when(categoriaData.existsByName("Piernas")).thenReturn(false);

        // Act
        categoriaBusiness.updateCategoriaMedidaCorporal(categoria);

        // Assert
        verify(categoriaData, times(1)).update(categoria);
    }
    @Test
    void updateCategoriaMedidaCorporal_InvalidId_ThrowsException() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setIdCategoria(0);  // ID inválido
        categoria.setNombreCategoria("Espalda");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.updateCategoriaMedidaCorporal(categoria));
        verify(categoriaData, never()).update(any());
    }
    @Test
    void updateCategoriaMedidaCorporal_DuplicateName_ThrowsException() {
        // Arrange
        CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
        categoria.setIdCategoria(2);
        categoria.setNombreCategoria("Abdomen");

        when(categoriaData.existsByName("Abdomen")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoriaBusiness.updateCategoriaMedidaCorporal(categoria));
        verify(categoriaData, never()).update(any());
    }


}// end
