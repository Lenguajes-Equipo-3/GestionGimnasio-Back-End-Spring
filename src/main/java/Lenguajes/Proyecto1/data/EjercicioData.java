package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.ImagenEjercicio;
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
                        new SqlParameter("id_categoria_ejercicio", Types.INTEGER),
                        new SqlParameter("nombre_ejercicio", Types.VARCHAR),
                        new SqlParameter("descripcion_ejercicio", Types.VARCHAR),
                        new SqlParameter("codigo_equipo", Types.VARCHAR),
                        new SqlOutParameter("id_ejercicio", Types.INTEGER)
                );

        Map<String, Object> outParameters = simpleJdbcCall.execute(
                Map.of(
                        "id_categoria_ejercicio", ejercicio.getCategoriaEjercicio().getIdCategoria(),
                        "nombre_ejercicio", ejercicio.getNombreEjercicio(),
                        "descripcion_ejercicio", ejercicio.getDescripcionEjercicio(),
                        "codigo_equipo", ejercicio.getCodigoEquipo()
                )
        );

        int idEjercicio = (int) outParameters.get("id_ejercicio");
        ejercicio.setIdEjercicio(idEjercicio);

        for (ImagenEjercicio imagen : ejercicio.getImagenes()) {
            saveImagen(idEjercicio, imagen);
        }
    }

    private void saveImagen(int idEjercicio, ImagenEjercicio imagen) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_RegistrarImagenEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_ejercicio", Types.INTEGER),
                        new SqlParameter("url_imagen", Types.VARCHAR),
                        new SqlParameter("descripcion_imagen", Types.VARCHAR),
                        new SqlOutParameter("id_imagen", Types.INTEGER)
                );

        Map<String, Object> outParameters = simpleJdbcCall.execute(
                Map.of(
                        "id_ejercicio", idEjercicio,
                        "url_imagen", imagen.getUrlImagen(),
                        "descripcion_imagen", imagen.getDescripcionImagen()
                )
        );

        imagen.setIdImagen((Integer) outParameters.get("id_imagen"));
    }

    @Transactional(readOnly = true)
    public Ejercicio findById(int idEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerEjercicioPorId")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_ejercicio", Types.INTEGER));

        Map<String, Object> result = simpleJdbcCall.execute(Map.of("id_ejercicio", idEjercicio));

        if (result == null || !result.containsKey("#result-set-1")) {
            return null;
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio((Integer) row.get("id_ejercicio"));

        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setIdCategoria((Integer) row.get("id_categoria_ejercicio"));
        categoria.setNombreCategoria((String) row.get("nombre_categoria"));
        categoria.setImagen((String) row.get("imagen"));
        ejercicio.setCategoriaEjercicio(categoria);

        ejercicio.setNombreEjercicio((String) row.get("nombre_ejercicio"));
        ejercicio.setDescripcionEjercicio((String) row.get("descripcion_ejercicio"));
        ejercicio.setCodigoEquipo((String) row.get("codigo_equipo"));
        ejercicio.setImagenes(findImagenesByEjercicioId(idEjercicio));

        return ejercicio;
    }

    @Transactional(readOnly = true)
    public Ejercicio findByName(String nombreEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerEjercicioPorNombre")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("@nombre_ejercicio", Types.VARCHAR));

        Map<String, Object> result = simpleJdbcCall.execute(Map.of("@nombre_ejercicio", nombreEjercicio));

        if (result == null || !result.containsKey("#result-set-1")) {
            return null;
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio((Integer) row.get("id_ejercicio"));

        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setIdCategoria((Integer) row.get("id_categoria_ejercicio"));
        categoria.setNombreCategoria((String) row.get("nombre_categoria"));
        categoria.setImagen((String) row.get("imagen"));
        ejercicio.setCategoriaEjercicio(categoria);

        ejercicio.setNombreEjercicio((String) row.get("nombre_ejercicio"));
        ejercicio.setDescripcionEjercicio((String) row.get("descripcion_ejercicio"));
        ejercicio.setCodigoEquipo((String) row.get("codigo_equipo"));
        ejercicio.setImagenes(findImagenesByEjercicioId(ejercicio.getIdEjercicio()));

        return ejercicio;
    }

    private List<ImagenEjercicio> findImagenesByEjercicioId(int idEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerImagenesPorEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_ejercicio", Types.INTEGER));

        Map<String, Object> result = simpleJdbcCall.execute(Map.of("id_ejercicio", idEjercicio));

        if (result == null || !result.containsKey("#result-set-1")) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        List<ImagenEjercicio> imagenes = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            ImagenEjercicio imagen = new ImagenEjercicio();
            imagen.setIdImagen((Integer) row.get("id_imagen"));
            imagen.setUrlImagen((String) row.get("url_imagen"));
            imagen.setDescripcionImagen((String) row.get("descripcion_imagen"));
            imagenes.add(imagen);
        }

        return imagenes;
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> findAll() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerTodosLosEjercicios")
                .withoutProcedureColumnMetaDataAccess();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) simpleJdbcCall.execute().get("#result-set-1");

        List<Ejercicio> ejercicios = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setIdEjercicio((int) row.get("id_ejercicio"));

            CategoriaEjercicio categoria = new CategoriaEjercicio();
            categoria.setIdCategoria((Integer) row.get("id_categoria_ejercicio"));
            categoria.setNombreCategoria((String) row.get("nombre_categoria"));
            categoria.setImagen((String) row.get("imagen"));
            ejercicio.setCategoriaEjercicio(categoria);

            ejercicio.setNombreEjercicio((String) row.get("nombre_ejercicio"));
            ejercicio.setDescripcionEjercicio((String) row.get("descripcion_ejercicio"));
            ejercicio.setCodigoEquipo((String) row.get("codigo_equipo"));
            ejercicio.setImagenes(findImagenesByEjercicioId(ejercicio.getIdEjercicio()));
            ejercicios.add(ejercicio);
        }

        return ejercicios;
    }

    @Transactional
    public void update(Ejercicio ejercicio) {
        SimpleJdbcCall updateEjercicioCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ActualizarEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_ejercicio", Types.INTEGER),
                        new SqlParameter("id_categoria_ejercicio", Types.INTEGER),
                        new SqlParameter("nombre_ejercicio", Types.VARCHAR),
                        new SqlParameter("descripcion_ejercicio", Types.VARCHAR),
                        new SqlParameter("codigo_equipo", Types.VARCHAR)
                );

        updateEjercicioCall.execute(
                Map.of(
                        "id_ejercicio", ejercicio.getIdEjercicio(),
                        "id_categoria_ejercicio", ejercicio.getCategoriaEjercicio().getIdCategoria(),
                        "nombre_ejercicio", ejercicio.getNombreEjercicio(),
                        "descripcion_ejercicio", ejercicio.getDescripcionEjercicio(),
                        "codigo_equipo", ejercicio.getCodigoEquipo()
                )
        );

        SimpleJdbcCall updateImagenesCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_EliminarImagenesEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_ejercicio", Types.INTEGER));

        updateImagenesCall.execute(Map.of("id_ejercicio", ejercicio.getIdEjercicio()));

        for (ImagenEjercicio imagen : ejercicio.getImagenes()) {
            saveImagen(ejercicio.getIdEjercicio(), imagen);
        }
    }

    @Transactional
    public void delete(int idEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_EliminarEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_ejercicio", Types.INTEGER)
                );

        simpleJdbcCall.execute(Map.of("id_ejercicio", idEjercicio));
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getImagenesByEjercicioId(int idEjercicio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerImagenesPorEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_ejercicio", Types.INTEGER));

        return (List<Map<String, Object>>) simpleJdbcCall.execute(Map.of("id_ejercicio", idEjercicio)).get("#result-set-1");
    }


}