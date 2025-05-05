package Lenguajes.Proyecto1.data;

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
        // Configurar el procedimiento almacenado
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_RegistrarEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_categoria_ejercicio", Types.INTEGER), // Parámetro de entrada
                        new SqlParameter("nombre_ejercicio", Types.VARCHAR),       // Parámetro de entrada
                        new SqlParameter("descripcion_ejercicio", Types.VARCHAR),  // Parámetro de entrada
                        new SqlParameter("codigo_equipo", Types.VARCHAR),          // Parámetro de entrada
                        new SqlOutParameter("id_ejercicio", Types.INTEGER)         // Parámetro de salida
                );

        // Ejecutar el procedimiento almacenado
        Map<String, Object> outParameters = simpleJdbcCall.execute(
                Map.of(
                        "id_categoria_ejercicio", ejercicio.getIdCategoriaEjercicio(),
                        "nombre_ejercicio", ejercicio.getNombreEjercicio(),
                        "descripcion_ejercicio", ejercicio.getDescripcionEjercicio(),
                        "codigo_equipo", ejercicio.getCodigoEquipo()
                )
        );

        // Obtener el ID generado
        int idEjercicio = (int) outParameters.get("id_ejercicio");
        ejercicio.setIdEjercicio(idEjercicio);

        // Guardar las imágenes asociadas
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

        // Asignar el ID generado al objeto ImagenEjercicio
        imagen.setIdImagen((Integer) outParameters.get("id_imagen"));
    }

    @Transactional(readOnly = true)
    public Ejercicio findById(int idEjercicio) {
        // Llamar al procedimiento almacenado
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerEjercicioPorId")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_ejercicio", Types.INTEGER));

        Map<String, Object> result = simpleJdbcCall.execute(Map.of("id_ejercicio", idEjercicio));

        // Validar si el resultado contiene datos
        if (result == null || !result.containsKey("#result-set-1")) {
            return null; // Retorna null si no hay datos
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows.isEmpty()) {
            return null; // Retorna null si no hay filas
        }

        // Mapear los datos del ejercicio
        Map<String, Object> row = rows.get(0);
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio((Integer) row.get("id_ejercicio"));
        ejercicio.setIdCategoriaEjercicio((Integer) row.get("id_categoria_ejercicio"));
        ejercicio.setNombreEjercicio((String) row.get("nombre_ejercicio"));
        ejercicio.setDescripcionEjercicio((String) row.get("descripcion_ejercicio"));
        ejercicio.setCodigoEquipo((String) row.get("codigo_equipo"));

        // Obtener las imágenes asociadas
        ejercicio.setImagenes(findImagenesByEjercicioId(idEjercicio));

        return ejercicio;
    }

    @Transactional(readOnly = true)
    public Ejercicio findByName(String nombreEjercicio) {
        // Llamar al procedimiento almacenado
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_ObtenerEjercicioPorNombre")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("@nombre_ejercicio", Types.VARCHAR));

        Map<String, Object> result = simpleJdbcCall.execute(Map.of("@nombre_ejercicio", nombreEjercicio));

        // Validar si el resultado contiene datos
        if (result == null || !result.containsKey("#result-set-1")) {
            return null; // Retorna null si no hay datos
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        if (rows.isEmpty()) {
            return null; // Retorna null si no hay filas
        }

        // Mapear los datos del ejercicio
        Map<String, Object> row = rows.get(0);
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio((Integer) row.get("id_ejercicio"));
        ejercicio.setIdCategoriaEjercicio((Integer) row.get("id_categoria_ejercicio"));
        ejercicio.setNombreEjercicio((String) row.get("nombre_ejercicio"));
        ejercicio.setDescripcionEjercicio((String) row.get("descripcion_ejercicio"));
        ejercicio.setCodigoEquipo((String) row.get("codigo_equipo"));

        // Obtener las imágenes asociadas
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
            return new ArrayList<>(); // Retorna una lista vacía si no hay imágenes
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");
        List<ImagenEjercicio> imagenes = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            ImagenEjercicio imagen = new ImagenEjercicio();
            imagen.setIdImagen((Integer) row.get("id_imagen"));
            imagen.setIdEjercicio((Integer) row.get("id_ejercicio"));
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
            ejercicio.setIdCategoriaEjercicio((int) row.get("id_categoria_ejercicio"));
            ejercicio.setNombreEjercicio((String) row.get("nombre_ejercicio"));
            ejercicio.setDescripcionEjercicio((String) row.get("descripcion_ejercicio"));
            ejercicio.setCodigoEquipo((String) row.get("codigo_equipo"));
            ejercicio.setImagenes(findImagenesByEjercicioId(ejercicio.getIdEjercicio()));
            ejercicios.add(ejercicio);
        }

        return ejercicios;
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

    @Transactional
    public void update(Ejercicio ejercicio) {
        // Actualizar el ejercicio
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
                        "id_categoria_ejercicio", ejercicio.getIdCategoriaEjercicio(),
                        "nombre_ejercicio", ejercicio.getNombreEjercicio(),
                        "descripcion_ejercicio", ejercicio.getDescripcionEjercicio(),
                        "codigo_equipo", ejercicio.getCodigoEquipo()
                )
        );

        // Actualizar las imágenes
        SimpleJdbcCall updateImagenesCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_EliminarImagenesEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter("id_ejercicio", Types.INTEGER));

        // Primero eliminar las imágenes existentes
        updateImagenesCall.execute(Map.of("id_ejercicio", ejercicio.getIdEjercicio()));

        // Insertar las nuevas imágenes
        for (ImagenEjercicio imagen : ejercicio.getImagenes()) {
            saveImagen(ejercicio.getIdEjercicio(), imagen);
        }
    }

    @Transactional
    public void delete(int idEjercicio) {
        // Configurar el procedimiento almacenado
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("sp_EliminarEjercicio")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("id_ejercicio", Types.INTEGER)
                );

        // Ejecutar el procedimiento almacenado
        simpleJdbcCall.execute(Map.of("id_ejercicio", idEjercicio));
    }


}
