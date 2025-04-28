package Lenguajes.Proyecto1.restController;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Lenguajes.Proyecto1.business.CategoriaEjercicioBusiness;
import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import Lenguajes.Proyecto1.dto.CategoriaEjercicioDTO;
import Lenguajes.Proyecto1.mapper.CategoriaEjercicioMapper;

@RestController
@RequestMapping("/api/categorias-ejercicio")
public class CategoriaEjercicioRestController  {

    @Autowired
    private CategoriaEjercicioBusiness categoriaEjercicioBusiness;

    @Autowired
    private CategoriaEjercicioMapper categoriaEjercicioMapper;

    // Crear nueva categoría de ejercicio
    @PostMapping
    public ResponseEntity<CategoriaEjercicioDTO> createCategoriaEjercicio(@Validated @RequestBody CategoriaEjercicioDTO categoriaEjercicioDTO) {
        CategoriaEjercicio categoriaEjercicio = categoriaEjercicioMapper.toEntity(categoriaEjercicioDTO);
        categoriaEjercicioBusiness.saveCategoriaEjercicio(categoriaEjercicio);
        return new ResponseEntity<>(categoriaEjercicioMapper.toDto(categoriaEjercicio), HttpStatus.CREATED);
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEjercicioDTO> getCategoriaEjercicioById(@PathVariable int id) {
        CategoriaEjercicio categoriaEjercicio = categoriaEjercicioBusiness.getCategoriaEjercicioById(id);
        return new ResponseEntity<>(categoriaEjercicioMapper.toDto(categoriaEjercicio), HttpStatus.OK);
    }

    // Obtener todas las categorías de ejercicio
    @GetMapping
    public ResponseEntity<List<CategoriaEjercicioDTO>> getAllCategoriaEjercicios() {
        List<CategoriaEjercicio> categoriasEjercicio = categoriaEjercicioBusiness.getAllCategoriaEjercicios();
        return new ResponseEntity<>(categoriasEjercicio.stream().map(categoriaEjercicioMapper::toDto).toList(), HttpStatus.OK);
    }

    // Actualizar categoría de ejercicio
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaEjercicioDTO> updateCategoriaEjercicio(@PathVariable int id, @Validated @RequestBody CategoriaEjercicioDTO categoriaEjercicioDTO) {
        CategoriaEjercicio categoriaEjercicio = categoriaEjercicioMapper.toEntity(categoriaEjercicioDTO);
        categoriaEjercicio.setIdCategoria(id);
        categoriaEjercicioBusiness.updateCategoriaEjercicio(categoriaEjercicio);
        return new ResponseEntity<>(categoriaEjercicioMapper.toDto(categoriaEjercicio), HttpStatus.OK);
    }

    // Eliminar categoría de ejercicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaEjercicio(@PathVariable int id) {
        categoriaEjercicioBusiness.deleteCategoriaEjercicio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
