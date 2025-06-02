package Lenguajes.Proyecto1.restController;


import Lenguajes.Proyecto1.business.RutinaBusiness;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.Rutina;
import Lenguajes.Proyecto1.dto.EjercicioDTO;
import Lenguajes.Proyecto1.dto.RutinaDTO;
import Lenguajes.Proyecto1.mapper.RutinaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rutinas")
@CrossOrigin(origins = "http://localhost:4200")
public class RutinaRestController {

    @Autowired
    private RutinaBusiness rutinaBusiness;

    @Autowired
    private RutinaMapper rutinaMapper;

    @PostMapping
    public ResponseEntity<RutinaDTO> createRutina(@Validated @RequestBody RutinaDTO rutinaDTO) {
        // Mapear el DTO a la entidad
        Rutina rutina = rutinaMapper.toRutina(rutinaDTO);

        // Guardar la rutina
        rutinaBusiness.saveRutina(rutina);

        // Retornar la respuesta
        return new ResponseEntity<>(rutinaMapper.toDto(rutina), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RutinaDTO>> getAllRutinas() {
        List<Rutina> rutinas = rutinaBusiness.getAllRutinas();
        return new ResponseEntity<>(rutinas.stream().map(rutinaMapper::toDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/{idRutina}")
    public ResponseEntity<RutinaDTO> getRutinaById(@PathVariable int idRutina) {
        Rutina rutina = rutinaBusiness.getRutinaById(idRutina);
        if (rutina == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rutinaMapper.toDto(rutina), HttpStatus.OK);
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<RutinaDTO>> getRutinaByEmpleadoId(@PathVariable int idEmpleado) {
        List<Rutina> rutinas = rutinaBusiness.getRutinasByEmpleadoId(idEmpleado);
        if (rutinas == null || rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rutinas.stream().map(rutinaMapper::toDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<RutinaDTO>> getRutinaByClienteId(@PathVariable int idCliente) {
        List<Rutina> rutinas = rutinaBusiness.getRutinasByClienteId(idCliente);
        if (rutinas == null || rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rutinas.stream().map(rutinaMapper::toDto).toList(), HttpStatus.OK);
    }

    @PutMapping("/{idRutina}")
    public ResponseEntity<RutinaDTO> updateRutina(@PathVariable int idRutina, @Validated @RequestBody RutinaDTO rutinaDTO) {
        // Verificar que el ID de la rutina en el DTO coincida con el ID de la ruta
        if (rutinaDTO.getIdRutina() != idRutina) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Mapear el DTO a la entidad
        Rutina rutina = rutinaMapper.toRutina(rutinaDTO);

        // Actualizar la rutina
        rutinaBusiness.updateRutina(rutina);

        // Retornar la respuesta
        return new ResponseEntity<>(rutinaMapper.toDto(rutina), HttpStatus.OK);
    }

    @DeleteMapping("/{idRutina}")
    public ResponseEntity<Void> deleteRutina(@PathVariable int idRutina) {
        // Verificar que la rutina existe
        Rutina rutina = rutinaBusiness.getRutinaById(idRutina);
        if (rutina == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Eliminar la rutina
        rutinaBusiness.deleteRutina(idRutina);

        // Retornar respuesta vacía con estado 204 No Content
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
