package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.ClienteData;
import Lenguajes.Proyecto1.domain.Cliente;
import Lenguajes.Proyecto1.dto.ClienteDTO;
import Lenguajes.Proyecto1.mapper.ClienteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClienteBusinessTest {

    @Mock
    private ClienteData clienteData;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteBusiness clienteBusiness;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Creación de un Cliente de prueba
        cliente = new Cliente(1, "123456789", "Juan", "Pérez", LocalDate.of(1990, 1, 1), 
                              "123456789", "Calle Falsa 123", "Ana", "987654321", "foto.jpg");

        // Creación de un ClienteDTO de prueba
        clienteDTO = new ClienteDTO(1,"123456789", "Juan", "Pérez", LocalDate.of(1990, 1, 1), 
                                    "123456789", "Calle Falsa 123", "Ana", "987654321", "foto.jpg");
    }

    @Test
    void obtenerTodosLosClientesTest() {
        // Simula el comportamiento de obtener todos los clientes
        when(clienteData.obtenerTodosLosClientes()).thenReturn(Arrays.asList(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        // Llamada al método que se está probando
        List<ClienteDTO> clientesDTO = clienteBusiness.obtenerTodosLosClientes();

        // Verificaciones
        assertNotNull(clientesDTO);
        assertEquals(1, clientesDTO.size());
        assertEquals("Juan", clientesDTO.get(0).getNombreCliente());
        
        // Verificar si se llamaron los métodos de las dependencias
        verify(clienteData, times(1)).obtenerTodosLosClientes();
        verify(clienteMapper, times(1)).toDTO(cliente);
    }

    @Test
    void obtenerClientePorIdTest() {
        // Simula el comportamiento de obtener un cliente por ID
        when(clienteData.obtenerClientePorId(1)).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        // Llamada al método que se está probando
        ClienteDTO clienteObtenido = clienteBusiness.obtenerClientePorId(1);

        // Verificaciones
        assertNotNull(clienteObtenido);
        assertEquals("Juan", clienteObtenido.getNombreCliente());

        // Verificar si se llamaron los métodos de las dependencias
        verify(clienteData, times(1)).obtenerClientePorId(1);
        verify(clienteMapper, times(1)).toDTO(cliente);
    }

    @Test
    void insertarClienteTest() {
        when(clienteMapper.toEntity(clienteDTO)).thenReturn(cliente); // ✅ Mock correcto

        clienteBusiness.insertarCliente(clienteDTO);

        verify(clienteMapper, times(1)).toEntity(clienteDTO);
        verify(clienteData, times(1)).insertarCliente(cliente);
    }

    @Test
    void actualizarClienteTest() {
        when(clienteMapper.toEntity(clienteDTO)).thenReturn(cliente); // ✅ Mock correcto

        clienteBusiness.actualizarCliente(clienteDTO);

        verify(clienteMapper, times(1)).toEntity(clienteDTO);
        verify(clienteData, times(1)).actualizarCliente(cliente);
    }


    @Test
    void eliminarClienteTest() {
        // Llamada al método que se está probando
        clienteBusiness.eliminarCliente(1);

        // Verificar si el método de la dependencia fue llamado
        verify(clienteData, times(1)).eliminarCliente(1);
    }
}
