package Lenguajes.Proyecto1.data;

import Lenguajes.Proyecto1.domain.Empleado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmpleadoDataTest {

    // IDs y Nombres de Roles fijos asumidos
    private static final int ROL_ID_ENTRENADOR = 1;
    private static final int ROL_ID_ADMIN = 2;
    private static final String ROL_NOMBRE_ENTRENADOR = "ENTRENADOR";
    private static final String ROL_NOMBRE_ADMIN = "ADMIN";

    @Autowired
    private EmpleadoData empleadoData;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private Empleado crearEntrenadorDePrueba(String sufijoUnico) {
        Empleado entrenador = new Empleado();
        entrenador.setNombreEmpleado("Entrenador");
        entrenador.setApellidosEmpleado("Prueba " + sufijoUnico);
        entrenador.setNombreUsuario("entrenador." + sufijoUnico + ".test");
        entrenador.setRolId(ROL_ID_ENTRENADOR);
        Empleado entrenadorGuardado = empleadoData.save(entrenador, "passEntrenador" + sufijoUnico);
        return empleadoData.findById(entrenadorGuardado.getIdEmpleado()).orElseThrow();
    }

    private Empleado crearAdminDePrueba(String sufijoUnico) {
        Empleado admin = new Empleado();
        admin.setNombreEmpleado("Admin");
        admin.setApellidosEmpleado("Prueba " + sufijoUnico);
        admin.setNombreUsuario("admin." + sufijoUnico + ".test");
        admin.setRolId(ROL_ID_ADMIN);
        Empleado adminGuardado = empleadoData.save(admin, "passAdmin" + sufijoUnico);
         return empleadoData.findById(adminGuardado.getIdEmpleado()).orElseThrow();
    }


    @Test
    @Sql(scripts = {"/limpiar_tablas_empleado_usuario_rol.sql"},
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void save_empleado_test() {
        // Arrange
        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setNombreEmpleado("Carlos");
        nuevoEmpleado.setApellidosEmpleado("Rodriguez");
        nuevoEmpleado.setNombreUsuario("carlos.r.test");
        nuevoEmpleado.setRolId(ROL_ID_ENTRENADOR);
        String contrasenaPlana = "password123";

        // Act: Guardar (asume que esto crea Empleado, Usuario y UsuarioRol)
        Empleado empleadoGuardado = empleadoData.save(nuevoEmpleado, contrasenaPlana);

        // Assert
        assertNotNull(empleadoGuardado);
        assertTrue(empleadoGuardado.getIdEmpleado() > 0);
        assertTrue(empleadoGuardado.getIdUsuario() > 0);
        assertEquals(ROL_ID_ENTRENADOR, empleadoGuardado.getRolId());

        Optional<Empleado> empleadoEncontradoOpt = empleadoData.findById(empleadoGuardado.getIdEmpleado());
        assertTrue(empleadoEncontradoOpt.isPresent());
        Empleado empleadoEncontrado = empleadoEncontradoOpt.get();
        assertEquals("Carlos", empleadoEncontrado.getNombreEmpleado());
        assertEquals("carlos.r.test", empleadoEncontrado.getNombreUsuario());
        assertEquals(ROL_ID_ENTRENADOR, empleadoEncontrado.getRolId());
        assertEquals(ROL_NOMBRE_ENTRENADOR, empleadoEncontrado.getNombreRol());

        // Verificar que UsuarioRol se creó (asume que save lo hizo)
         Integer rolIdUsuario = jdbcTemplate.queryForObject(
             "SELECT rol_id FROM UsuarioRol WHERE usuario_id = ?", Integer.class, empleadoGuardado.getIdUsuario());
         assertEquals(ROL_ID_ENTRENADOR, rolIdUsuario);
    }

    @Test
    @Sql(scripts = {"/limpiar_tablas_empleado_usuario_rol.sql"},
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void find_by_id_test() {
        // Arrange: Crear datos (save crea todo)
        Empleado adminCreado = crearAdminDePrueba("findById");
        int idAdminGenerado = adminCreado.getIdEmpleado();

        // Act
        Optional<Empleado> empleadoOpt = empleadoData.findById(idAdminGenerado);

        // Assert
        assertTrue(empleadoOpt.isPresent());
        Empleado empleado = empleadoOpt.get();
        assertEquals(idAdminGenerado, empleado.getIdEmpleado());
        assertEquals("Admin", empleado.getNombreEmpleado());
        assertEquals("Prueba findById", empleado.getApellidosEmpleado());
        assertEquals("admin.findById.test", empleado.getNombreUsuario());
        assertEquals(ROL_ID_ADMIN, empleado.getRolId());
        assertEquals(ROL_NOMBRE_ADMIN, empleado.getNombreRol());
        assertEquals(adminCreado.getIdUsuario(), empleado.getIdUsuario());
    }

    @Test
    @Sql(scripts = {"/limpiar_tablas_empleado_usuario_rol.sql"},
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void find_by_id_not_found_test() {
        int idInexistente = 9999;
        Optional<Empleado> empleadoOpt = empleadoData.findById(idInexistente);
        assertFalse(empleadoOpt.isPresent());
    }

    @Test
    @Sql(scripts = {"/limpiar_tablas_empleado_usuario_rol.sql"},
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void find_all_test() {
        // Arrange
        Empleado entrenador = crearEntrenadorDePrueba("findAll1");
        Empleado admin = crearAdminDePrueba("findAll2");

        // Act
        List<Empleado> empleados = empleadoData.findAll();

        // Assert
        assertNotNull(empleados);
        assertEquals(2, empleados.size());
         assertTrue(empleados.stream().anyMatch(e -> e.getIdEmpleado() == entrenador.getIdEmpleado() && "entrenador.findAll1.test".equals(e.getNombreUsuario()) && e.getRolId() == ROL_ID_ENTRENADOR && ROL_NOMBRE_ENTRENADOR.equals(e.getNombreRol())));
         assertTrue(empleados.stream().anyMatch(e -> e.getIdEmpleado() == admin.getIdEmpleado() && "admin.findAll2.test".equals(e.getNombreUsuario()) && e.getRolId() == ROL_ID_ADMIN && ROL_NOMBRE_ADMIN.equals(e.getNombreRol())));
    }

    @Test
    @Sql(scripts = {"/limpiar_tablas_empleado_usuario_rol.sql"},
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void update_empleado_sin_contrasena_test() {
        // Arrange
        Empleado empleadoAActualizar = crearEntrenadorDePrueba("updateNoPass");
        int idEmpleadoAActualizar = empleadoAActualizar.getIdEmpleado();
        int idUsuarioOriginal = empleadoAActualizar.getIdUsuario();
        int nuevoRolId = ROL_ID_ADMIN;

        empleadoAActualizar.setNombreEmpleado("Entrenador Actualizado");
        empleadoAActualizar.setApellidosEmpleado("Prueba Updated");
        empleadoAActualizar.setNombreUsuario("entrenador.upd.test");
        empleadoAActualizar.setRolId(nuevoRolId);

        // Act: Asume que sp_ActualizarEmpleado también actualiza UsuarioRol si cambia rol_id
        empleadoData.update(empleadoAActualizar, null);

        // Assert
        Optional<Empleado> empleadoActualizadoOpt = empleadoData.findById(idEmpleadoAActualizar);
        assertTrue(empleadoActualizadoOpt.isPresent());
        Empleado empleadoActualizado = empleadoActualizadoOpt.get();
        assertEquals("Entrenador Actualizado", empleadoActualizado.getNombreEmpleado());
        assertEquals("Prueba Updated", empleadoActualizado.getApellidosEmpleado());
        assertEquals("entrenador.upd.test", empleadoActualizado.getNombreUsuario());
        assertEquals(nuevoRolId, empleadoActualizado.getRolId());
        assertEquals(ROL_NOMBRE_ADMIN, empleadoActualizado.getNombreRol());
        assertEquals(idUsuarioOriginal, empleadoActualizado.getIdUsuario());
        // Verificar que UsuarioRol se actualizó (asume que el SP lo hizo)
        Integer rolIdUsuarioActual = jdbcTemplate.queryForObject( "SELECT rol_id FROM UsuarioRol WHERE usuario_id = ?", Integer.class, idUsuarioOriginal);
        assertEquals(nuevoRolId, rolIdUsuarioActual, "El rol en UsuarioRol no se actualizó.");
    }

    @Test
    @Sql(scripts = {"/limpiar_tablas_empleado_usuario_rol.sql"},
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void update_empleado_con_contrasena_test() {
        // Arrange
        Empleado empleadoAActualizar = crearAdminDePrueba("updatePass");
        int idEmpleadoAActualizar = empleadoAActualizar.getIdEmpleado();
        int idUsuarioOriginal = empleadoAActualizar.getIdUsuario();
        empleadoAActualizar.setNombreEmpleado("Admin Updated");
        String nuevaContrasena = "nuevaPass456";

        // Act
        empleadoData.update(empleadoAActualizar, nuevaContrasena);

        // Assert
        Optional<Empleado> empleadoActualizadoOpt = empleadoData.findById(idEmpleadoAActualizar);
        assertTrue(empleadoActualizadoOpt.isPresent());
        // ... resto de aserciones sin cambios ...
         Empleado empleadoActualizado = empleadoActualizadoOpt.get();
         assertEquals("Admin Updated", empleadoActualizado.getNombreEmpleado());
         assertEquals(ROL_ID_ADMIN, empleadoActualizado.getRolId());
         assertEquals(idUsuarioOriginal, empleadoActualizado.getIdUsuario());
    }

    @Test
    @Sql(scripts = {"/limpiar_tablas_empleado_usuario_rol.sql"},
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_empleado_test() {
        // Arrange
        Empleado entrenador = crearEntrenadorDePrueba("delete");
        Empleado admin = crearAdminDePrueba("delete");
        int idEmpleadoAEliminar = entrenador.getIdEmpleado();
        int idUsuarioAEliminar = entrenador.getIdUsuario();
        int idAdminSobreviviente = admin.getIdEmpleado();
        assertTrue(empleadoData.findById(idEmpleadoAEliminar).isPresent());

        // Act: Asume que sp_EliminarEmpleado maneja FKs correctamente
        empleadoData.delete(idEmpleadoAEliminar);

        // Assert
        Optional<Empleado> empleadoEliminadoOpt = empleadoData.findById(idEmpleadoAEliminar);
        assertFalse(empleadoEliminadoOpt.isPresent());

        // Verificar que las dependencias se eliminaron (si el SP lo hizo)
        assertEquals(0, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Usuario WHERE id_empleado = ?", Integer.class, idEmpleadoAEliminar), "El registro en Usuario asociado debería haberse eliminado.");
        assertEquals(0, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM UsuarioRol WHERE usuario_id = ?", Integer.class, idUsuarioAEliminar), "El registro en UsuarioRol asociado debería haberse eliminado.");

        // Verificar que el otro empleado sigue existiendo
        assertTrue(empleadoData.findById(idAdminSobreviviente).isPresent());
        assertEquals(1, empleadoData.findAll().size());
    }
}