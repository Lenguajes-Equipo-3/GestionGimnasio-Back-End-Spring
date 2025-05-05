package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RolData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerRoles")
                .returningResultSet("#result-set-1",
                        (rs, rowNum) -> { // RowMapper lambda
                            Rol rol = new Rol();
                            rol.setRolId(rs.getInt("rol_id"));
                            rol.setNombreRol(rs.getString("nombre_rol"));
                            return rol;
                        });

        Map<String, Object> result = simpleJdbcCall.execute();

        // Obtener la lista del result set
        List<Rol> roles = (List<Rol>) result.get("#result-set-1");

        return (roles != null) ? roles : new ArrayList<>(); // Devuelve la lista o una vacía si es null
    }

}