package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.*;

@Repository
public class ClienteData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insertarCliente(Cliente cliente) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_InsertarCliente")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("numero_identificacion", Types.NVARCHAR),
                        new SqlParameter("nombre_cliente", Types.NVARCHAR),
                        new SqlParameter("apellidos_cliente", Types.NVARCHAR),
                        new SqlParameter("fecha_nacimiento", Types.DATE),
                        new SqlParameter("telefono", Types.NVARCHAR),
                        new SqlParameter("direccion", Types.NVARCHAR),
                        new SqlParameter("nombre_contacto_emergencia", Types.NVARCHAR),
                        new SqlParameter("telefono_contacto_emergencia", Types.NVARCHAR),
                        new SqlParameter("fotografia", Types.NVARCHAR)
                );

        jdbcCall.execute(Map.of(
                "numero_identificacion", cliente.getNumeroIdentificacion(),
                "nombre_cliente", cliente.getNombreCliente(),
                "apellidos_cliente", cliente.getApellidosCliente(),
                "fecha_nacimiento", cliente.getFechaNacimiento(),
                "telefono", cliente.getTelefono(),
                "direccion", cliente.getDireccion(),
                "nombre_contacto_emergencia", cliente.getNombreContactoEmergencia(),
                "telefono_contacto_emergencia", cliente.getTelefonoContactoEmergencia(),
                "fotografia", cliente.getFotografia()
        ));
    }

    @Transactional
    public void actualizarCliente(Cliente cliente) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ActualizarCliente")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_cliente", Types.INTEGER),
                        new SqlParameter("numero_identificacion", Types.NVARCHAR),
                        new SqlParameter("nombre_cliente", Types.NVARCHAR),
                        new SqlParameter("apellidos_cliente", Types.NVARCHAR),
                        new SqlParameter("fecha_nacimiento", Types.DATE),
                        new SqlParameter("telefono", Types.NVARCHAR),
                        new SqlParameter("direccion", Types.NVARCHAR),
                        new SqlParameter("nombre_contacto_emergencia", Types.NVARCHAR),
                        new SqlParameter("telefono_contacto_emergencia", Types.NVARCHAR),
                        new SqlParameter("fotografia", Types.NVARCHAR)
                );

        jdbcCall.execute(Map.of(
                "id_cliente", cliente.getIdCliente(),
                "numero_identificacion", cliente.getNumeroIdentificacion(),
                "nombre_cliente", cliente.getNombreCliente(),
                "apellidos_cliente", cliente.getApellidosCliente(),
                "fecha_nacimiento", cliente.getFechaNacimiento(),
                "telefono", cliente.getTelefono(),
                "direccion", cliente.getDireccion(),
                "nombre_contacto_emergencia", cliente.getNombreContactoEmergencia(),
                "telefono_contacto_emergencia", cliente.getTelefonoContactoEmergencia(),
                "fotografia", cliente.getFotografia()
        ));
    }

    @Transactional
    public void eliminarCliente(int idCliente) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_EliminarCliente")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_cliente", Types.INTEGER)
                );

        jdbcCall.execute(Map.of("id_cliente", idCliente));
    }

    @Transactional(readOnly = true)
    public Cliente obtenerClientePorId(int idCliente) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerClientePorId")
                .returningResultSet("cliente", (rs, rowNum) -> {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                    cliente.setNombreCliente(rs.getString("nombre_cliente"));
                    cliente.setApellidosCliente(rs.getString("apellidos_cliente"));
                    cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setNombreContactoEmergencia(rs.getString("nombre_contacto_emergencia"));
                    cliente.setTelefonoContactoEmergencia(rs.getString("telefono_contacto_emergencia"));
                    cliente.setFotografia(rs.getString("fotografia"));
                    return cliente;
                });

        Map<String, Object> result = jdbcCall.execute(Map.of("id_cliente", idCliente));
        List<Cliente> clientes = (List<Cliente>) result.get("cliente");

        return (clientes == null || clientes.isEmpty()) ? null : clientes.get(0);
    }

    @Transactional(readOnly = true)
    public List<Cliente> obtenerTodosLosClientes() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerTodosLosClientes");

        Map<String, Object> result = jdbcCall.execute();
        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");

        List<Cliente> clientes = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente((Integer) row.get("id_cliente"));
            cliente.setNumeroIdentificacion((String) row.get("numero_identificacion"));
            cliente.setNombreCliente((String) row.get("nombre_cliente"));
            cliente.setApellidosCliente((String) row.get("apellidos_cliente"));
            cliente.setFechaNacimiento(((java.sql.Date) row.get("fecha_nacimiento")).toLocalDate());
            cliente.setTelefono((String) row.get("telefono"));
            cliente.setDireccion((String) row.get("direccion"));
            cliente.setNombreContactoEmergencia((String) row.get("nombre_contacto_emergencia"));
            cliente.setTelefonoContactoEmergencia((String) row.get("telefono_contacto_emergencia"));
            cliente.setFotografia((String) row.get("fotografia"));
            clientes.add(cliente);
        }

        return clientes;
    }
}
