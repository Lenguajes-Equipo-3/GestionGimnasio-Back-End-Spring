package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class CategoriaEjercicioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(CategoriaEjercicio categoriaEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_CrearCategoriaEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("nombre_categoria", Types.NVARCHAR),
                        new SqlParameter("imagen", Types.NVARCHAR)
                );

        simpleJdbcCall.execute(
                Map.of(
                        "nombre_categoria", categoriaEjercicio.getNombreCategoria(),
                        "imagen", categoriaEjercicio.getImagen()
                )
        );
    }

    @Transactional(readOnly = true)
    public CategoriaEjercicio findById(int idCategoria) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerCategoriaEjercicioPorId")
                .returningResultSet("categoria",
                        (rs, rowNum) -> {
                            CategoriaEjercicio c = new CategoriaEjercicio();
                            c.setIdCategoria(rs.getInt("id_categoria"));
                            c.setNombreCategoria(rs.getString("nombre_categoria"));
                            c.setImagen(rs.getString("imagen"));
                            return c;
                        });

        Map<String, Object> result = simpleJdbcCall.execute(Collections.singletonMap("IdCategoria", idCategoria));

        List<CategoriaEjercicio> categorias = (List<CategoriaEjercicio>) result.get("categoria");

        if (categorias == null || categorias.isEmpty()) {
            return null;
        }

        return categorias.get(0);
    }


    @Transactional(readOnly = true)
    public List<CategoriaEjercicio> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ConsultarCategoriaEjercicio");

        Map<String, Object> result = simpleJdbcCall.execute();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        List<CategoriaEjercicio> categorias = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            CategoriaEjercicio categoria = new CategoriaEjercicio();
            categoria.setIdCategoria((int) row.get("id_categoria"));
            categoria.setNombreCategoria((String) row.get("nombre_categoria"));
            categoria.setImagen((String) row.get("imagen"));
            categorias.add(categoria);
        }

        return categorias;
    }

    public void update(CategoriaEjercicio categoriaEjercicio) {
        try {
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo") // usa el esquema, no el catalogo
                .withProcedureName("sp_ActualizarCategoriaEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                    new SqlParameter("@id_categoria", Types.INTEGER),
                    new SqlParameter("@nombre_categoria", Types.VARCHAR),
                    new SqlParameter("@imagen", Types.VARCHAR)
                );

            simpleJdbcCall.execute(
                Map.of(
                    "@id_categoria", categoriaEjercicio.getIdCategoria(),
                    "@nombre_categoria", categoriaEjercicio.getNombreCategoria(),
                    "@imagen", categoriaEjercicio.getImagen()
                )
            );
        } catch (Exception e) {
            System.err.println("❌ Error en updateCategoriaEjercicio: " + e.getMessage());
            throw e;
        }
    }




    @Transactional
    public void delete(int idCategoria) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_EliminarCategoriaEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("@id_categoria", Types.INTEGER)
                );

        simpleJdbcCall.execute(idCategoria);
    }
}
