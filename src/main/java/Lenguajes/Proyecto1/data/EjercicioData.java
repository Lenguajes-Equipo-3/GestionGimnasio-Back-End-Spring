package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Ejercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class EjercicioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(Ejercicio ejercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_RegistrarEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("id_ejercicio", Types.INTEGER),
                        new SqlParameter("id_categoria_ejercicio", Types.INTEGER),
                        new SqlParameter("nombre_ejercicio", Types.VARCHAR),
                        new SqlParameter("descripcion_ejercicio", Types.VARCHAR),
                        new SqlParameter("codigo_equipo", Types.VARCHAR)
                );

        Map<String, Object> outParameters = simpleJdbcCall.execute(
                Map.of(
                        "id_categoria_ejercicio", ejercicio.getIdCategoriaEjercicio(),
                        "nombre_ejercicio", ejercicio.getNombreEjercicio(),
                        "descripcion_ejercicio", ejercicio.getDescripcionEjercicio(),
                        "codigo_equipo", ejercicio.getCodigoEquipo()
                )
        );

        Object idEjercicio = outParameters.get("id_ejercicio");
        if (idEjercicio == null) {
            throw new IllegalStateException("El procedimiento almacenado no devolvió el ID del ejercicio.");
        }

        ejercicio.setIdEjercicio((Integer) idEjercicio);
    }

    @Transactional(readOnly = true)
    public Ejercicio findById(int idEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerEjercicioPorId")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_ejercicio", Types.INTEGER)
                );

        Map<String, Object> result = simpleJdbcCall.execute(idEjercicio);

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio((int) result.get("id_ejercicio"));
        ejercicio.setIdCategoriaEjercicio((int) result.get("id_categoria_ejercicio"));
        ejercicio.setNombreEjercicio((String) result.get("nombre_ejercicio"));
        ejercicio.setDescripcionEjercicio((String) result.get("descripcion_ejercicio"));
        ejercicio.setCodigoEquipo((String) result.get("codigo_equipo"));

        return ejercicio;
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerEjercicios");

        Map<String, Object> result = simpleJdbcCall.execute();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        List<Ejercicio> ejercicios = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setIdEjercicio((int) row.get("id_ejercicio"));
            ejercicio.setIdCategoriaEjercicio((int) row.get("id_categoria_ejercicio"));
            ejercicio.setNombreEjercicio((String) row.get("nombre_ejercicio"));
            ejercicio.setDescripcionEjercicio((String) row.get("descripcion_ejercicio"));
            ejercicio.setCodigoEquipo((String) row.get("codigo_equipo"));
            ejercicios.add(ejercicio);
        }

        return ejercicios;
    }

    @Transactional
    public void update(Ejercicio ejercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ActualizarEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_ejercicio", Types.INTEGER),
                        new SqlParameter("@id_categoria_ejercicio", Types.INTEGER),
                        new SqlParameter("@nombre_ejercicio", Types.VARCHAR),
                        new SqlParameter("@descripcion_ejercicio", Types.VARCHAR),
                        new SqlParameter("@codigo_equipo", Types.VARCHAR)
                );

        simpleJdbcCall.execute(
                ejercicio.getIdEjercicio(),
                ejercicio.getIdCategoriaEjercicio(),
                ejercicio.getNombreEjercicio(),
                ejercicio.getDescripcionEjercicio(),
                ejercicio.getCodigoEquipo()
        );
    }

    @Transactional
    public void delete(int idEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_EliminarEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_ejercicio", Types.INTEGER)
                );

        simpleJdbcCall.execute(idEjercicio);
    }

}
