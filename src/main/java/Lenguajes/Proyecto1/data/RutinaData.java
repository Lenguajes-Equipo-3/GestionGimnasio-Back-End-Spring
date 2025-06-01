package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RutinaData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmpleadoData empleadoData;

    @Autowired
    private ClienteData clienteData;

    @Autowired
    private EjercicioData ejercicioData;

    @Autowired
    private MedidaCorporalData medidaCorporalData;

    @Transactional
    public void save(Rutina rutina) {
        // Guardar la rutina principal
        SimpleJdbcCall rutinaCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_RegistrarRutina")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_empleado", Types.INTEGER),
                        new SqlParameter("@id_cliente", Types.INTEGER),
                        new SqlParameter("@fecha_creacion", Types.DATE),
                        new SqlParameter("@fecha_renovacion", Types.DATE),
                        new SqlParameter("@objetivo", Types.VARCHAR),
                        new SqlParameter("@lesiones", Types.VARCHAR),
                        new SqlParameter("@enfermedades", Types.VARCHAR),
                        new SqlParameter("@es_vigente", Types.BIT),
                        new SqlOutParameter("@id_rutina", Types.INTEGER)
                );

        Map<String, Object> out = rutinaCall.execute(
                Map.of(
                        "@id_empleado", rutina.getEmpleado().getIdEmpleado(),
                        "@id_cliente", rutina.getCliente().getIdCliente(),
                        "@fecha_creacion", rutina.getFechaCreacion(),
                        "@fecha_renovacion", rutina.getFechaRenovacion(),
                        "@objetivo", rutina.getObjetivo(),
                        "@lesiones", rutina.getLesiones(),
                        "@enfermedades", rutina.getEnfermedades(),
                        "@es_vigente", rutina.isEsVigente()
                )
        );

        int idRutina = (int) out.get("@id_rutina");
        rutina.setIdRutina(idRutina);

        // Guardar los ejercicios de la rutina
        for (ItemRutinaEjercicio item : rutina.getEjercicios()) {
            SimpleJdbcCall itemEjercicioCall = new SimpleJdbcCall(jdbcTemplate)
                    .withSchemaName("dbo")
                    .withProcedureName("sp_RegistrarItemRutinaEjercicio")
                    .withoutProcedureColumnMetaDataAccess()
                    .declareParameters(
                            new SqlParameter("@id_rutina", Types.INTEGER),
                            new SqlParameter("@id_ejercicio", Types.INTEGER),
                            new SqlParameter("@series", Types.INTEGER),
                            new SqlParameter("@repeticiones", Types.INTEGER),
                            new SqlParameter("@codigo_equipo", Types.VARCHAR)
                    );
            itemEjercicioCall.execute(
                    Map.of(
                            "@id_rutina", idRutina,
                            "@id_ejercicio", item.getEjercicio().getIdEjercicio(),
                            "@series", item.getSeries(),
                            "@repeticiones", item.getRepeticiones(),
                            "@codigo_equipo", item.getCodigoEquipo()
                    )
            );
        }

        // Guardar las medidas de la rutina
        for (ItemRutinaMedida item : rutina.getMedidas()) {
            SimpleJdbcCall itemMedidaCall = new SimpleJdbcCall(jdbcTemplate)
                    .withSchemaName("dbo")
                    .withProcedureName("sp_RegistrarItemRutinaMedida")
                    .withoutProcedureColumnMetaDataAccess()
                    .declareParameters(
                            new SqlParameter("@id_medida", Types.INTEGER),
                            new SqlParameter("@id_rutina", Types.INTEGER),
                            new SqlParameter("@valor", Types.FLOAT)
                    );
            itemMedidaCall.execute(
                    Map.of(
                            "@id_medida", item.getMedidaCorporal().getIdMedida(),
                            "@id_rutina", idRutina,
                            "@valor", item.getValor()
                    )
            );
        }
    }

    @Transactional(readOnly = true)
    public List<Rutina> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerRutinas");

        Map<String, Object> result = simpleJdbcCall.execute();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        List<Rutina> rutinas = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Rutina rutina = new Rutina();
            rutina.setIdRutina((int) row.get("id_rutina"));

            // Mapear empleado
            Empleado empleado = new Empleado();
            int empleadoId= (int) row.get("id_empleado");
            empleado = empleadoData.findById(empleadoId).orElse(null);;
            rutina.setEmpleado(empleado);

            // Mapear cliente
            Cliente cliente = new Cliente();
            int clienteId = (int) row.get("id_cliente");
            rutina.setCliente(clienteData.obtenerClientePorId(clienteId));

            // Mapear fechas
            rutina.setFechaCreacion(((java.sql.Date) row.get("fecha_creacion")).toLocalDate());
            rutina.setFechaRenovacion(((java.sql.Date) row.get("fecha_renovacion")).toLocalDate());

            // Mapear objetivo, lesiones, enfermedades, esVigente
            rutina.setObjetivo((String) row.get("objetivo"));
            rutina.setLesiones((String) row.get("lesiones"));
            rutina.setEnfermedades((String) row.get("enfermedades"));
            rutina.setEsVigente((boolean) row.get("es_vigente"));

            // Mapear ejercicios y medidas (debes hacer consultas adicionales o joins)
            rutina.setEjercicios(this.findItemRutinaEjercicioByRutinaId(rutina.getIdRutina()));
            rutina.setMedidas(this.findItemRutinaMedidaByRutinaId(rutina.getIdRutina()));

            rutinas.add(rutina);
        }

        return rutinas;
    }

    @Transactional
    public Rutina findById(int idRutina) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerRutinaPorId")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_rutina", Types.INTEGER));

        Map<String, Object> params = Map.of("id_rutina", idRutina);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows == null || rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Rutina rutina = new Rutina();
        rutina.setIdRutina((int) row.get("id_rutina"));

        // Mapear empleado
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado((int) row.get("id_empleado"));
        empleado = empleadoData.findById(empleado.getIdEmpleado()).orElse(null);
        rutina.setEmpleado(empleado);

        // Mapear cliente
        Cliente cliente = new Cliente();
        cliente.setIdCliente((int) row.get("id_cliente"));
        cliente = clienteData.obtenerClientePorId(cliente.getIdCliente());
        rutina.setCliente(cliente);

        // Mapear fechas
        rutina.setFechaCreacion(((java.sql.Date) row.get("fecha_creacion")).toLocalDate());
        rutina.setFechaRenovacion(((java.sql.Date) row.get("fecha_renovacion")).toLocalDate());

        // Mapear objetivo, lesiones, enfermedades, esVigente
        rutina.setObjetivo((String) row.get("objetivo"));
        rutina.setLesiones((String) row.get("lesiones"));
        rutina.setEnfermedades((String) row.get("enfermedades"));
        rutina.setEsVigente((boolean) row.get("es_vigente"));

        // Mapear ejercicios y medidas (debes hacer consultas adicionales o joins)
        rutina.setEjercicios(this.findItemRutinaEjercicioByRutinaId(rutina.getIdRutina()));
        rutina.setMedidas(this.findItemRutinaMedidaByRutinaId(rutina.getIdRutina()));

        return rutina;
    }

    @Transactional
    public Rutina findRutinaByEmpleadoId(int idEmpleado) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerRutinaPorEmpleadoId")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_empleado", Types.INTEGER));

        Map<String, Object> params = Map.of("id_empleado", idEmpleado);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows == null || rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Rutina rutina = new Rutina();
        rutina.setIdRutina((int) row.get("id_rutina"));

        // Mapear empleado
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado((int) row.get("id_empleado"));
        empleado = empleadoData.findById(empleado.getIdEmpleado()).orElse(null);
        rutina.setEmpleado(empleado);

        // Mapear cliente
        Cliente cliente = new Cliente();
        cliente.setIdCliente((int) row.get("id_cliente"));
        cliente = clienteData.obtenerClientePorId(cliente.getIdCliente());
        rutina.setCliente(cliente);

        // Mapear fechas
        rutina.setFechaCreacion(((java.sql.Date) row.get("fecha_creacion")).toLocalDate());
        rutina.setFechaRenovacion(((java.sql.Date) row.get("fecha_renovacion")).toLocalDate());

        // Mapear objetivo, lesiones, enfermedades, esVigente
        rutina.setObjetivo((String) row.get("objetivo"));
        rutina.setLesiones((String) row.get("lesiones"));
        rutina.setEnfermedades((String) row.get("enfermedades"));
        rutina.setEsVigente((boolean) row.get("es_vigente"));

        // Mapear ejercicios y medidas (debes hacer consultas adicionales o joins)
        rutina.setEjercicios(this.findItemRutinaEjercicioByRutinaId(rutina.getIdRutina()));
        rutina.setMedidas(this.findItemRutinaMedidaByRutinaId(rutina.getIdRutina()));

        return rutina;
    }

    @Transactional
    public Rutina findRutinaByClienteId(int idCliente) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerRutinaPorClienteId")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_cliente", Types.INTEGER));

        Map<String, Object> params = Map.of("id_cliente", idCliente);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows == null || rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Rutina rutina = new Rutina();
        rutina.setIdRutina((int) row.get("id_rutina"));

        // Mapear empleado
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado((int) row.get("id_empleado"));
        empleado = empleadoData.findById(empleado.getIdEmpleado()).orElse(null);
        rutina.setEmpleado(empleado);

        // Mapear cliente
        Cliente cliente = new Cliente();
        cliente.setIdCliente((int) row.get("id_cliente"));
        cliente = clienteData.obtenerClientePorId(cliente.getIdCliente());
        rutina.setCliente(cliente);

        // Mapear fechas
        rutina.setFechaCreacion(((java.sql.Date) row.get("fecha_creacion")).toLocalDate());
        rutina.setFechaRenovacion(((java.sql.Date) row.get("fecha_renovacion")).toLocalDate());

        // Mapear objetivo, lesiones, enfermedades, esVigente
        rutina.setObjetivo((String) row.get("objetivo"));
        rutina.setLesiones((String) row.get("lesiones"));
        rutina.setEnfermedades((String) row.get("enfermedades"));
        rutina.setEsVigente((boolean) row.get("es_vigente"));

        // Mapear ejercicios y medidas (debes hacer consultas adicionales o joins)
        rutina.setEjercicios(this.findItemRutinaEjercicioByRutinaId(rutina.getIdRutina()));
        rutina.setMedidas(this.findItemRutinaMedidaByRutinaId(rutina.getIdRutina()));

        return rutina;
    }


    private List<ItemRutinaEjercicio> findItemRutinaEjercicioByRutinaId(int idRutina) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerItemsRutinaEjercicioPorRutinaId")
                .returningResultSet("items", (rs, rowNum) -> {
                    ItemRutinaEjercicio item = new ItemRutinaEjercicio();
                    Ejercicio ejercicio = ejercicioData.findById(rs.getInt("id_ejercicio"));
                    item.setEjercicio(ejercicio);
                    item.setSeries(rs.getInt("series"));
                    item.setRepeticiones(rs.getInt("repeticiones"));
                    item.setCodigoEquipo(rs.getString("codigo_equipo"));
                    return item;
                });

        Map<String, Object> result = jdbcCall.execute(Map.of("id_rutina", idRutina));
        List<ItemRutinaEjercicio> items = (List<ItemRutinaEjercicio>) result.get("items");
        return items != null ? items : new ArrayList<>();
    }

    private List<ItemRutinaMedida> findItemRutinaMedidaByRutinaId(int idRutina) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerItemsRutinaMedidaPorRutinaId")
                .returningResultSet("items", (rs, rowNum) -> {
                    ItemRutinaMedida item = new ItemRutinaMedida();
                    MedidaCorporal medida = medidaCorporalData.findById(rs.getInt("id_medida"));
                    item.setMedidaCorporal(medida);
                    item.setValor(rs.getFloat("valor"));
                    return item;
                });

        Map<String, Object> result = jdbcCall.execute(Map.of("id_rutina", idRutina));
        List<ItemRutinaMedida> items = (List<ItemRutinaMedida>) result.get("items");
        return items != null ? items : new ArrayList<>();
    }




}


