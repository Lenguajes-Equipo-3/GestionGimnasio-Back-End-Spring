package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.EmpleadoBusiness;
import Lenguajes.Proyecto1.domain.Empleado;
import Lenguajes.Proyecto1.dto.EmpleadoDTO;
import Lenguajes.Proyecto1.mapper.EmpleadoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoRestController {

    @Autowired
    private EmpleadoBusiness empleadoBusiness;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @PostMapping
    public ResponseEntity<EmpleadoDTO> createEmpleado(@Validated @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            Empleado empleadoEntity = empleadoMapper.toEntity(empleadoDTO);
            // Pasa la contraseña directamente desde el DTO
            Empleado savedEmpleado = empleadoBusiness.saveEmpleado(empleadoEntity, empleadoDTO.getContrasena());
            // El DTO devuelto no tendrá contraseña (gracias al mapper)
            return new ResponseEntity<>(empleadoMapper.toDto(savedEmpleado), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
             System.err.println("Error al crear empleado: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al crear el empleado", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> getEmpleadoById(@PathVariable int id) {
        try {
            Empleado empleado = empleadoBusiness.getEmpleadoById(id);
            return new ResponseEntity<>(empleadoMapper.toDto(empleado), HttpStatus.OK);
         } catch (IllegalArgumentException e) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
         } catch (RuntimeException e) {
             if (e.getMessage().contains("no encontrado")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
             }
             System.err.println("Error al obtener empleado " + id + ": " + e.getMessage());
             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al obtener empleado", e);
         }
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> getAllEmpleados() {
        List<Empleado> empleados = empleadoBusiness.getAllEmpleados();
        return new ResponseEntity<>(empleadoMapper.toDtoList(empleados), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> updateEmpleado(@PathVariable int id, @Validated @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            Empleado empleadoEntity = empleadoMapper.toEntity(empleadoDTO);
            empleadoEntity.setIdEmpleado(id);

            // Pasa la contraseña directamente desde el DTO
            empleadoBusiness.updateEmpleado(empleadoEntity, empleadoDTO.getContrasena());

            Empleado updatedEmpleado = empleadoBusiness.getEmpleadoById(id);
            return new ResponseEntity<>(empleadoMapper.toDto(updatedEmpleado), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
             if (e.getMessage().contains("no encontrado")) {
                 throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
             }
             System.err.println("Error al actualizar empleado " + id + ": " + e.getMessage());
             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al actualizar el empleado", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable int id) {
         try {
            empleadoBusiness.deleteEmpleado(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (IllegalArgumentException e) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
         } catch (RuntimeException e) {
             if (e.getMessage().contains("no encontrado")) {
                 throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
             }
              System.err.println("Error al eliminar empleado " + id + ": " + e.getMessage());
             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al eliminar el empleado", e);
         }
    }
}