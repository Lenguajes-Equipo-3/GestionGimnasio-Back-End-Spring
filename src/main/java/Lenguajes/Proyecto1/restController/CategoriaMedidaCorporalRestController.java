package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.CategoriaMedidaCorporalBusiness;
import Lenguajes.Proyecto1.domain.CategoriaMedidaCorporal;
import Lenguajes.Proyecto1.dto.CategoriaMedidaCorporalDTO;
import Lenguajes.Proyecto1.mapper.CategoriaMedidaCorporalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoriasmedidascorporales")
@CrossOrigin(origins = "http://localhost:4200")  // Permite solicitudes desde localhost:4200

public class CategoriaMedidaCorporalRestController {

    @Autowired
    private CategoriaMedidaCorporalBusiness categoriaMedidaCorporalBusiness;

    @Autowired
    private CategoriaMedidaCorporalMapper categoriaMedidaCorporalMapper;

    @PostMapping
    public ResponseEntity<CategoriaMedidaCorporalDTO> createCategoriaMedidaCorporal(
            @Validated @RequestBody CategoriaMedidaCorporalDTO categoriaMedidaCorporalDTO) throws Exception {
        CategoriaMedidaCorporal categoriaMedidaCorporal = categoriaMedidaCorporalMapper.toEntity(categoriaMedidaCorporalDTO);
        categoriaMedidaCorporalBusiness.save(categoriaMedidaCorporal);
        return new ResponseEntity<>(categoriaMedidaCorporalMapper.toDto(categoriaMedidaCorporal), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMedidaCorporalDTO> getCategoriaMedidaCorporalById(@PathVariable int id) {
        CategoriaMedidaCorporal categoriaMedidaCorporal = categoriaMedidaCorporalBusiness.getCategoriaMedidaCorporalById(id);
        return new ResponseEntity<>(categoriaMedidaCorporalMapper.toDto(categoriaMedidaCorporal), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaMedidaCorporalDTO>> getAllCategoriasMedidasCorporales() {
        List<CategoriaMedidaCorporal> categorias = categoriaMedidaCorporalBusiness.getAllCategoriasMedidasCorporales();
        return new ResponseEntity<>(categorias.stream().map(categoriaMedidaCorporalMapper::toDto).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaMedidaCorporalDTO> updateCategoriaMedidaCorporal(
            @PathVariable int id, @Validated @RequestBody CategoriaMedidaCorporalDTO categoriaMedidaCorporalDTO) {

        CategoriaMedidaCorporal categoriaMedidaCorporal = categoriaMedidaCorporalMapper.toEntity(categoriaMedidaCorporalDTO);
        categoriaMedidaCorporal.setIdCategoria(id);
        categoriaMedidaCorporalBusiness.updateCategoriaMedidaCorporal(categoriaMedidaCorporal);
        return new ResponseEntity<>(categoriaMedidaCorporalMapper.toDto(categoriaMedidaCorporal), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaMedidaCorporal(@PathVariable int id) {
        categoriaMedidaCorporalBusiness.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
