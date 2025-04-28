package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClienteData {

    private final JdbcTemplate jdbcTemplate;

    public ClienteData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapper para convertir resultados de la consulta en objetos Cliente
    private RowMapper<Cliente> clienteRowMapper = (rs, rowNum) -> {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("idCliente"));
        cliente.setNumeroIdentificacion(rs.getString("numeroIdentificacion"));
        cliente.setNombreCliente(rs.getString("nombreCliente"));
        cliente.setApellidosCliente(rs.getString("apellidosCliente"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setNombreContactoEmergencia(rs.getString("nombreContactoEmergencia"));
        cliente.setTelefonoContactoEmergencia(rs.getString("telefonoContactoEmergencia"));
        cliente.setFotografia(rs.getString("fotografia"));
        return cliente;
    };

    // 1. Obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        String sql = "CALL obtenerTodosClientes()";  // Llamada al procedimiento almacenado
        return jdbcTemplate.query(sql, clienteRowMapper);
    }

    // 2. Obtener un cliente por su ID
    public Cliente obtenerClientePorId(int idCliente) {
        String sql = "CALL obtenerClientePorId(?)";  // Llamada al procedimiento almacenado
        return jdbcTemplate.queryForObject(sql, new Object[]{idCliente}, clienteRowMapper);
    }

    // 3. Insertar un cliente
    public void insertarCliente(Cliente cliente) {
        String sql = "CALL insertarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?)";  // Procedimiento almacenado
        jdbcTemplate.update(sql,
                cliente.getNumeroIdentificacion(),
                cliente.getNombreCliente(),
                cliente.getApellidosCliente(),
                cliente.getFechaNacimiento(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getNombreContactoEmergencia(),
                cliente.getTelefonoContactoEmergencia(),
                cliente.getFotografia());
    }

    // 4. Actualizar un cliente
    public void actualizarCliente(Cliente cliente) {
        String sql = "CALL actualizarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  // Procedimiento almacenado
        jdbcTemplate.update(sql,
                cliente.getIdCliente(),
                cliente.getNumeroIdentificacion(),
                cliente.getNombreCliente(),
                cliente.getApellidosCliente(),
                cliente.getFechaNacimiento(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getNombreContactoEmergencia(),
                cliente.getTelefonoContactoEmergencia(),
                cliente.getFotografia());
    }

    // 5. Eliminar un cliente
    public void eliminarCliente(int idCliente) {
        String sql = "CALL eliminarCliente(?)";  // Procedimiento almacenado
        jdbcTemplate.update(sql, idCliente);
    }
}