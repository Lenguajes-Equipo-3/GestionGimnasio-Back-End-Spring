package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.CategoriaMedidaCorporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class CategoriaMedidaCorporalData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(CategoriaMedidaCorporal categoriaMedidaCorporal) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_CrearCategoriaMedida")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("nombre_categoria", Types.NVARCHAR)
                );

        simpleJdbcCall.execute(
                Map.of(
                        "nombre_categoria", categoriaMedidaCorporal.getNombreCategoria()
                )
        );
    }

    @Transactional(readOnly = true)
    public CategoriaMedidaCorporal findById(int idCategoria) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ObtenerCategoriaMedidaPorId")
                .returningResultSet("categoria",
                        (rs, rowNum) -> {
                            CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
                            categoria.setIdCategoria(rs.getInt("id_categoria"));
                            categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                            return categoria;
                        }
                );

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("id_categoria", idCategoria); // ojo que aquí no lleves @

        Map<String, Object> result = simpleJdbcCall.execute(inParams);

// Obtener el resultado
        List<CategoriaMedidaCorporal> categorias = (List<CategoriaMedidaCorporal>) result.get("categoria");

        if (categorias == null || categorias.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada con id: " + idCategoria);
        }

        return categorias.get(0);

    }


    @Transactional(readOnly = true)
    public List<CategoriaMedidaCorporal> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ConsultarCategoriasMedida");

        Map<String, Object> result = simpleJdbcCall.execute();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        List<CategoriaMedidaCorporal> categorias = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            CategoriaMedidaCorporal categoria = new CategoriaMedidaCorporal();
            categoria.setIdCategoria((int) row.get("id_categoria"));
            categoria.setNombreCategoria((String) row.get("nombre_categoria"));
            categorias.add(categoria);
        }

        return categorias;
    }


    @Transactional
    public void update(CategoriaMedidaCorporal categoriaMedidaCorporal) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ActualizarCategoriaMedida")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_categoria", Types.INTEGER),
                        new SqlParameter("nombre_categoria", Types.NVARCHAR)
                );
        simpleJdbcCall.execute(
                Map.of(
                        "id_categoria", categoriaMedidaCorporal.getIdCategoria(),
                        "nombre_categoria", categoriaMedidaCorporal.getNombreCategoria()
                )
        );
    }

    @Transactional
    public void delete(int idCategoria) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_EliminarCategoriaMedida")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("@id_categoria", Types.INTEGER));
        simpleJdbcCall.execute(idCategoria);
    }


    // Verificar si el nombre de la categoría ya existe
    @Transactional(readOnly = true)
    public boolean existsByName(String nombreCategoria) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_ExisteCategoriaMedida")
                .declareParameters(new SqlParameter("nombre_categoria", Types.NVARCHAR));

        Map<String, Object> result = simpleJdbcCall.execute(Map.of("nombre_categoria", nombreCategoria));

        // Asegurarse de que el resultado contiene la clave "existe_categoria" y verificar su valor
        if (result != null && result.containsKey("existe_categoria")) {
            Integer existeCategoria = (Integer) result.get("existe_categoria");
            return existeCategoria != null && existeCategoria == 1;
        }

        return false;
    }

}
