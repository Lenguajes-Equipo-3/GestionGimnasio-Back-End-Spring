package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.EjercicioBusiness;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.dto.EjercicioDTO;
import Lenguajes.Proyecto1.mapper.EjercicioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioRestController {

    @Autowired
    private EjercicioBusiness ejercicioBusiness;

    @Autowired
    private EjercicioMapper ejercicioMapper;

    @PostMapping
    public ResponseEntity<EjercicioDTO> createEjercicio(@Validated @RequestBody EjercicioDTO ejercicioDTO) {
        Ejercicio ejercicio = ejercicioMapper.toEntity(ejercicioDTO);
        ejercicioBusiness.saveEjercicio(ejercicio);
        return new ResponseEntity<>(ejercicioMapper.toDto(ejercicio), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EjercicioDTO> getEjercicioById(@PathVariable int id) {
        Ejercicio ejercicio = ejercicioBusiness.getEjercicioById(id);
        return new ResponseEntity<>(ejercicioMapper.toDto(ejercicio), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EjercicioDTO>> getAllEjercicios() {
        List<Ejercicio> ejercicios = ejercicioBusiness.getAllEjercicios();
        return new ResponseEntity<>(ejercicios.stream().map(ejercicioMapper::toDto).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EjercicioDTO> updateEjercicio(@PathVariable int id, @Validated @RequestBody EjercicioDTO ejercicioDTO) {
        Ejercicio ejercicio = ejercicioMapper.toEntity(ejercicioDTO);
        ejercicio.setIdEjercicio(id);
        ejercicioBusiness.updateEjercicio(ejercicio);
        return new ResponseEntity<>(ejercicioMapper.toDto(ejercicio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEjercicio(@PathVariable int id) {
        ejercicioBusiness.deleteEjercicio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}