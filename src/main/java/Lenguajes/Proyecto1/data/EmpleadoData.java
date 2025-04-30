package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmpleadoData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public Empleado save(Empleado empleado, String contrasena) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_RegistrarEmpleado")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@nombre_empleado", Types.VARCHAR),
                        new SqlParameter("@apellidos_empleado", Types.VARCHAR),
                        new SqlParameter("@usuario", Types.VARCHAR),
                        new SqlParameter("@contrasena", Types.VARCHAR),
                        new SqlParameter("@id_rol", Types.INTEGER),
                        new SqlOutParameter("@id_empleado", Types.INTEGER),
                        new SqlOutParameter("@usuario_id", Types.INTEGER) 
                );

        // Crear el mapa de parámetros de entrada
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("@nombre_empleado", empleado.getNombreEmpleado());
        parameters.addValue("@apellidos_empleado", empleado.getApellidosEmpleado());
        parameters.addValue("@usuario", empleado.getNombreUsuario());
        parameters.addValue("@contrasena", contrasena); 
        parameters.addValue("@id_rol", empleado.getRolId()); 

        // Ejecutar la llamada
        Map<String, Object> outParameters = simpleJdbcCall.execute(parameters);

        // Obtener los IDs generados y asignarlos al objeto empleado
        Integer idEmpleadoGenerado = (Integer) outParameters.get("@id_empleado");
        Integer usuarioIdGenerado = (Integer) outParameters.get("@usuario_id");

        if (idEmpleadoGenerado != null) {
            empleado.setIdEmpleado(idEmpleadoGenerado);
        }
        if (usuarioIdGenerado != null) {
            empleado.setIdUsuario(usuarioIdGenerado);
        }

        return empleado;
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> findById(int idEmpleado) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerEmpleadoPorId")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_empleado", Types.INTEGER)
                );

        try {
            Map<String, Object> result = simpleJdbcCall.execute(Map.of("@id_empleado", idEmpleado));

            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

            if (resultSet != null && !resultSet.isEmpty()) {
                 Map<String, Object> row = resultSet.get(0); // Obtiene la primera (y única) fila
                 return Optional.of(mapRowToEmpleado(row)); // Mapea la fila a un objeto Empleado
            } else if (!result.isEmpty() && result.containsKey("id_empleado")) {
                 // Caso alternativo: el SP devuelve las columnas directamente en el mapa raíz
                 return Optional.of(mapRowToEmpleado(result));
            } else {
                 return Optional.empty();
            }

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception e) {
             System.err.println("Error al buscar empleado por ID: " + idEmpleado + " - " + e.getMessage());
             return Optional.empty();
        }
    }


    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerTodosLosEmpleados");

        Map<String, Object> result = simpleJdbcCall.execute();
        List<Empleado> empleados = new ArrayList<>();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");

        if (rows != null) {
             for (Map<String, Object> row : rows) {
                 empleados.add(mapRowToEmpleado(row));
             }
         }

        return empleados;
    }

    @Transactional
    public void update(Empleado empleado, String contrasena) { 
         SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ActualizarEmpleado")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_empleado", Types.INTEGER),
                        new SqlParameter("@nombre_empleado", Types.VARCHAR),
                        new SqlParameter("@apellidos_empleado", Types.VARCHAR),
                        new SqlParameter("@usuario", Types.VARCHAR),
                        new SqlParameter("@contrasena", Types.VARCHAR),
                        new SqlParameter("@id_rol", Types.INTEGER)
                );

        // Crear el mapa de parámetros
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("@id_empleado", empleado.getIdEmpleado());
        parameters.addValue("@nombre_empleado", empleado.getNombreEmpleado());
        parameters.addValue("@apellidos_empleado", empleado.getApellidosEmpleado());
        parameters.addValue("@usuario", empleado.getNombreUsuario());
        parameters.addValue("@contrasena", contrasena);
        parameters.addValue("@id_rol", empleado.getRolId());

        simpleJdbcCall.execute(parameters);
    }

    @Transactional
    public void delete(int idEmpleado) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_EliminarEmpleado")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_empleado", Types.INTEGER)
                );

        simpleJdbcCall.execute(Map.of("@id_empleado", idEmpleado));
    }

    // Método auxiliar para mapear una fila de resultado a un Empleado
    private Empleado mapRowToEmpleado(Map<String, Object> row) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado((Integer) row.get("id_empleado"));
        empleado.setNombreEmpleado((String) row.get("nombre_empleado"));
        empleado.setApellidosEmpleado((String) row.get("apellidos_empleado"));

        // Datos del Usuario asociado
        empleado.setIdUsuario((Integer) row.get("usuario_id"));
        empleado.setNombreUsuario((String) row.get("usuario"));

        // Datos del Rol asociado
        empleado.setRolId((Integer) row.get("rol_id"));
        empleado.setNombreRol((String) row.get("nombre_rol"));

        return empleado;
    }
}