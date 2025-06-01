package Lenguajes.Proyecto1.restController;


import Lenguajes.Proyecto1.business.RutinaBusiness;
import Lenguajes.Proyecto1.domain.Rutina;
import Lenguajes.Proyecto1.dto.RutinaDTO;
import Lenguajes.Proyecto1.mapper.RutinaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

}
