package Lenguajes.Proyecto1.business;


import Lenguajes.Proyecto1.data.MedidaCorporalData;
import Lenguajes.Proyecto1.domain.MedidaCorporal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class MedidaCorporalBusinessTest {
    @Mock
    private MedidaCorporalData medidaCorporalData;
    @InjectMocks
    private MedidaCorporalBusiness medidaCorporalBusiness;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveMedidaCorporal_ValidData_SavesSuccessfully() throws Exception {
        // Arrange
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("Altura");
        medida.setUnidadMedida("cm");

        // Act
        medidaCorporalBusiness.saveMedidaCorporal(medida);

        // Assert
        verify(medidaCorporalData, times(1)).save(medida);
    }

    @Test
    void saveMedidaCorporal_EmptyNombre_ThrowsException() throws Exception {
        // Arrange
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("");  // Inválido
        medida.setUnidadMedida("cm");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> medidaCorporalBusiness.saveMedidaCorporal(medida));
        verify(medidaCorporalData, never()).save(any());
    }
    @Test
    void saveMedidaCorporal_EmptyUnidad_ThrowsException() throws Exception {
        // Arrange
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("Altura");
        medida.setUnidadMedida("");  // Inválido

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> medidaCorporalBusiness.saveMedidaCorporal(medida));
        verify(medidaCorporalData, never()).save(any());
    }
    @Test
    void getMedidaCorporalById_ValidId_ReturnsMedida() {
        // Arrange
        MedidaCorporal medida = new MedidaCorporal();
        medida.setIdMedida(1);
        medida.setNombreMedida("Peso");
        medida.setUnidadMedida("kg");

        when(medidaCorporalData.findById(1)).thenReturn(medida);

        // Act
        MedidaCorporal result = medidaCorporalBusiness.getMedidaCorporalById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdMedida());
        assertEquals("Peso", result.getNombreMedida());
        assertEquals("kg", result.getUnidadMedida());
    }
    @Test
    void getMedidaCorporalById_InvalidId_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> medidaCorporalBusiness.getMedidaCorporalById(0));
        verify(medidaCorporalData, never()).findById(anyInt());
    }

    @Test
    void updateMedidaCorporal_ValidData_CallsUpdate() {
        // Arrange
        MedidaCorporal medida = new MedidaCorporal();
        medida.setIdMedida(1);
        medida.setNombreMedida("Peso");
        medida.setUnidadMedida("kg");

        // Act
        medidaCorporalBusiness.updateMedidaCorporal(medida);

        // Assert
        verify(medidaCorporalData, times(1)).update(medida);
    }
    @Test
    void updateMedidaCorporal_EmptyNombre_ThrowsException() {
        // Arrange
        MedidaCorporal medida = new MedidaCorporal();
        medida.setIdMedida(1);
        medida.setNombreMedida("");
        medida.setUnidadMedida("kg");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> medidaCorporalBusiness.updateMedidaCorporal(medida));
        verify(medidaCorporalData, never()).update(any());
    }

    @Test
    void updateMedidaCorporal_EmptyUnidad_ThrowsException() {
        // Arrange
        MedidaCorporal medida = new MedidaCorporal();
        medida.setIdMedida(1);
        medida.setNombreMedida("Peso");
        medida.setUnidadMedida("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> medidaCorporalBusiness.updateMedidaCorporal(medida));
        verify(medidaCorporalData, never()).update(any());
    }
    @Test
    void deleteMedidaCorporal_ValidId_CallsDelete() {
        // Arrange
        int idValido = 1;

        // Act
        medidaCorporalBusiness.deleteMedidaCorporal(idValido);

        // Assert
        verify(medidaCorporalData, times(1)).delete(idValido);
    }
    @Test
    void deleteMedidaCorporal_InvalidId_ThrowsException() {
        // Arrange
        int idInvalido = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> medidaCorporalBusiness.deleteMedidaCorporal(idInvalido));
        verify(medidaCorporalData, never()).delete(anyInt());
    }

}//end
