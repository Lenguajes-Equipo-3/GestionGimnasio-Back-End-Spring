package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.MedidaCorporalBusiness;
import Lenguajes.Proyecto1.domain.MedidaCorporal;
import Lenguajes.Proyecto1.dto.MedidaCorporalDTO;
import Lenguajes.Proyecto1.mapper.MedidaCorporalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medidascorporales")
@CrossOrigin(origins = "http://localhost:4200")  // Permite solicitudes desde localhost:4200

public class MedidaCorporalRestController {

    @Autowired
    private MedidaCorporalBusiness medidaCorporalBusiness;

    @Autowired
    private MedidaCorporalMapper medidaCorporalMapper;

    @PostMapping
    public ResponseEntity<MedidaCorporalDTO> createMedidaCorporal(@Validated @RequestBody MedidaCorporalDTO medidaCorporalDTO) throws Exception {
        MedidaCorporal medidaCorporal = medidaCorporalMapper.toEntity(medidaCorporalDTO);
        medidaCorporalBusiness.saveMedidaCorporal(medidaCorporal);
        return new ResponseEntity<>(medidaCorporalMapper.toDto(medidaCorporal), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedidaCorporalDTO> getMedidaCorporalById(@PathVariable int id) {
        MedidaCorporal medidaCorporal = medidaCorporalBusiness.getMedidaCorporalById(id);
        return new ResponseEntity<>(medidaCorporalMapper.toDto(medidaCorporal), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MedidaCorporalDTO>> getAllMedidasCorporales() {
        List<MedidaCorporal> medidasCorporales = medidaCorporalBusiness.getAllMedidasCorporales();
        return new ResponseEntity<>(medidasCorporales.stream().map(medidaCorporalMapper::toDto).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedidaCorporalDTO> updateMedidaCorporal(@PathVariable int id, @Validated @RequestBody MedidaCorporalDTO medidaCorporalDTO) {
        MedidaCorporal medidaCorporal = medidaCorporalMapper.toEntity(medidaCorporalDTO);
        medidaCorporal.setIdMedida(id);
        medidaCorporalBusiness.updateMedidaCorporal(medidaCorporal);
        return new ResponseEntity<>(medidaCorporalMapper.toDto(medidaCorporal), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedidaCorporal(@PathVariable int id) {
        medidaCorporalBusiness.deleteMedidaCorporal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
