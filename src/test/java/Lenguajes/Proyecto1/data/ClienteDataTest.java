package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteDataTest {

    @Autowired
    private ClienteData clienteData;

    @Test
    @Sql(scripts = "/limpiar_tabla_cliente.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void insertar_cliente_test() {
        Cliente cliente = new Cliente();
        cliente.setNumeroIdentificacion("123456789");
        cliente.setNombreCliente("Ana");
        cliente.setApellidosCliente("Sánchez");
        cliente.setFechaNacimiento(LocalDate.of(1992, 4, 10));
        cliente.setTelefono("8888-1234");
        cliente.setDireccion("San José");
        cliente.setNombreContactoEmergencia("Carlos");
        cliente.setTelefonoContactoEmergencia("8888-5678");
        cliente.setFotografia("ana.jpg");

        clienteData.insertarCliente(cliente);

        List<Cliente> clientes = clienteData.obtenerTodosLosClientes();
        assertEquals(1, clientes.size());
        assertEquals("Ana", clientes.get(0).getNombreCliente());
    }

    @Test
    @Sql(scripts = {"/limpiar_tabla_cliente.sql", "/insertar_clientes_prueba.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void obtener_cliente_por_id_test() {
        Cliente cliente = clienteData.obtenerClientePorId(1);
        assertNotNull(cliente);
        assertEquals("Juan", cliente.getNombreCliente());
    }

    @Test
    @Sql(scripts = {"/limpiar_tabla_cliente.sql", "/insertar_clientes_prueba.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void actualizar_cliente_test() {
        Cliente cliente = clienteData.obtenerClientePorId(1);
        cliente.setNombreCliente("Juan Carlos");
        cliente.setTelefono("7000-9999");

        clienteData.actualizarCliente(cliente);

        Cliente actualizado = clienteData.obtenerClientePorId(1);
        assertEquals("Juan Carlos", actualizado.getNombreCliente());
        assertEquals("7000-9999", actualizado.getTelefono());
    }

    @Test
    @Sql(scripts = {"/limpiar_tabla_cliente.sql", "/insertar_clientes_prueba.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void eliminar_cliente_test() {
        // Asegúrate de que el cliente existe antes de eliminarlo
        Cliente clienteAntesDeEliminar = clienteData.obtenerClientePorId(1);
        assertNotNull(clienteAntesDeEliminar, "El cliente no existe antes de la eliminación");

        // Eliminar cliente
        clienteData.eliminarCliente(1);
        
        // Verificar que el cliente fue eliminado
        Cliente clienteEliminado = clienteData.obtenerClientePorId(1);
        assertNull(clienteEliminado, "El cliente no fue eliminado correctamente");
    }


    @Test
    @Sql(scripts = {"/limpiar_tabla_cliente.sql", "/insertar_clientes_prueba.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void obtener_todos_los_clientes_test() {
        List<Cliente> clientes = clienteData.obtenerTodosLosClientes();
        assertEquals(2, clientes.size());
    }
}
