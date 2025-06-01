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
    public ResponseEntity<RutinaDTO> getRutinaByEmpleadoId(@PathVariable int idEmpleado) {
        Rutina rutina = rutinaBusiness.getRutinaByEmpleadoId(idEmpleado);
        if (rutina == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rutinaMapper.toDto(rutina), HttpStatus.OK);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<RutinaDTO> getRutinaByClienteId(@PathVariable int idCliente) {
        Rutina rutina = rutinaBusiness.getRutinaByClienteId(idCliente);
        if (rutina == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rutinaMapper.toDto(rutina), HttpStatus.OK);
    }

}
