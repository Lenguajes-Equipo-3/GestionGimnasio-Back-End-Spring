package Lenguajes.Proyecto1.restController;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Lenguajes.Proyecto1.business.CategoriaEjercicioBusiness;
import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import Lenguajes.Proyecto1.dto.CategoriaEjercicioDTO;
import Lenguajes.Proyecto1.mapper.CategoriaEjercicioMapper;
import Lenguajes.Proyecto1.service.ImageStorageService;

import org.springframework.web.bind.annotation.RequestPart;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Permite CORS solo para este controlador

@RequestMapping("/api/categorias-ejercicio")
public class CategoriaEjercicioRestController  {

    @Autowired
    private CategoriaEjercicioBusiness categoriaEjercicioBusiness;

    @Autowired
    private CategoriaEjercicioMapper categoriaEjercicioMapper;
    
    @Autowired
    private ImageStorageService imageStorageService;


    // Crear nueva categoría de ejercicio
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoriaEjercicioDTO> createCategoriaEjercicio(
            @RequestPart("categoria") CategoriaEjercicioDTO categoriaEjercicioDTO,
            @RequestPart("imagen") MultipartFile imagen) {
    	
    	   
    	
    	
        try {
            // Guardar imagen
        	if (imagen != null && !imagen.isEmpty()) {
        	String rutaRelativa = imageStorageService.saveImage(imagen, "categorias");

            // Asociar imagen al DTO
            categoriaEjercicioDTO.setImagen(rutaRelativa);
        	}
            // Convertir y guardar
            CategoriaEjercicio categoria = categoriaEjercicioMapper.toEntity(categoriaEjercicioDTO);
            categoriaEjercicioBusiness.saveCategoriaEjercicio(categoria);

            return new ResponseEntity<>(categoriaEjercicioMapper.toDto(categoria), HttpStatus.CREATED);
        } catch (IOException e) {
        	  e.printStackTrace(); // Esto ayuda a ver la causa en consola
        	    return ResponseEntity.internalServerError().build();
        }
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
    @PutMapping("/actualizar")
    public ResponseEntity<CategoriaEjercicioDTO> updateCategoriaEjercicio(
            @RequestPart("categoriaDTO") CategoriaEjercicioDTO categoriaEjercicioDTO,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        CategoriaEjercicio categoriaEjercicio = categoriaEjercicioMapper.toEntity(categoriaEjercicioDTO);

        if (imagen != null && !imagen.isEmpty()) {
            // Guardar la nueva imagen y establecer la ruta en la entidad
            String rutaImagen;
			try {
				rutaImagen = imageStorageService.saveImage(imagen, "categorias");
				categoriaEjercicio.setImagen(rutaImagen);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }

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
