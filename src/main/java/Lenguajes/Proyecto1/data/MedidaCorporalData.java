package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.MedidaCorporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MedidaCorporalData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public MedidaCorporal save(MedidaCorporal medida) throws Exception {
        try {
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_RegistrarMedidaCorporal")
                    .withSchemaName("dbo")
                    .declareParameters(
                            new SqlParameter("nombre_medida", Types.VARCHAR),
                            new SqlParameter("unidad_medida", Types.VARCHAR),
                            new SqlParameter("es_obligatoria", Types.BIT),
                            new SqlParameter("id_categoria", Types.INTEGER),
                            new SqlOutParameter("id_medida", Types.INTEGER)
                    );

            Map<String, Object> inParameters = new HashMap<>();
            inParameters.put("nombre_medida", medida.getNombreMedida());
            inParameters.put("unidad_medida", medida.getUnidadMedida());
            inParameters.put("es_obligatoria", medida.getEsObligatoria());
            inParameters.put("id_categoria", medida.getIdCategoria());

            Map<String, Object> outParameters = simpleJdbcCall.execute(inParameters);

            medida.setIdMedida((Integer) outParameters.get("id_medida"));

            return medida;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MedidaCorporal findById(int idMedida) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerMedidaCorporalPorId")
                .declareParameters(new SqlParameter("id_medida", Types.INTEGER));

        // Crea un Map para pasar el parámetro
        Map<String, Object> params = new HashMap<>();
        params.put("id_medida", idMedida);

        Map<String, Object> result = null;
        try {
            result = simpleJdbcCall.execute(params);
        } catch (Exception e) {
            // Manejo de excepciones, por ejemplo loguear el error
            System.err.println("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
        }

        if (result == null || result.isEmpty()) {
            // Si no se encuentra el resultado
            return null;
        }

        // Asegurándote de que la clave "#result-set-1" es correcta
        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows == null || rows.isEmpty()) {
            return null;
        }
        MedidaCorporal medida = new MedidaCorporal();
        Map<String, Object> row = rows.get(0);
        medida.setIdMedida((int) row.get("id_medida"));
        medida.setNombreMedida((String) row.get("nombre_medida"));
        medida.setUnidadMedida((String) row.get("unidad_medida"));
        medida.setEsObligatoria((boolean) row.get("es_obligatoria"));
        medida.setIdCategoria((int) row.get("id_categoria"));

        return medida;
    }




    @Transactional(readOnly = true)
    public List<MedidaCorporal> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerMedidasCorporales");

        Map<String, Object> result = simpleJdbcCall.execute();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        List<MedidaCorporal> medidas = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            MedidaCorporal medida = new MedidaCorporal();
            medida.setIdMedida((int) row.get("id_medida"));
            medida.setNombreMedida((String) row.get("nombre_medida"));
            medida.setUnidadMedida((String) row.get("unidad_medida"));
            medida.setEsObligatoria((boolean) row.get("es_obligatoria"));
            medida.setIdCategoria((int) row.get("id_categoria"));
            medidas.add(medida);
        }

        return medidas;
    }

    @Transactional
    public void update(MedidaCorporal medida) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ActualizarMedidaCorporal")
                .declareParameters(
                        new SqlParameter("id_medida", Types.INTEGER),
                        new SqlParameter("nombre_medida", Types.VARCHAR),
                        new SqlParameter("unidad_medida", Types.VARCHAR),
                        new SqlParameter("es_obligatoria", Types.BIT),
                        new SqlParameter("id_categoria", Types.INTEGER)
                );

        simpleJdbcCall.execute(
                medida.getIdMedida(),
                medida.getNombreMedida(),
                medida.getUnidadMedida(),
                medida.getEsObligatoria(),
                medida.getIdCategoria()
        );
    }

    @Transactional
    public void delete(int idMedida) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_EliminarMedidaCorporal")
                .declareParameters(new SqlParameter("id_medida", Types.INTEGER));

        simpleJdbcCall.execute(idMedida);
    }


}
