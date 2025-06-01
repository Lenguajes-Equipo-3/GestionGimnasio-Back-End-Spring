package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.EjercicioBusiness;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.dto.CategoriaEjercicioDTO;
import Lenguajes.Proyecto1.dto.EjercicioDTO;
import Lenguajes.Proyecto1.mapper.EjercicioMapper;
import Lenguajes.Proyecto1.service.ImageStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ejercicios")
@CrossOrigin(origins = "http://localhost:4200")
public class EjercicioRestController {

	@Autowired
	private EjercicioBusiness ejercicioBusiness;

	@Autowired
	private EjercicioMapper ejercicioMapper;

	@Autowired
	private ImageStorageService imageStorageService;

	@PostMapping
	public ResponseEntity<EjercicioDTO> createEjercicio(@RequestPart("ejercicio") EjercicioDTO ejercicioDTO,
			@RequestPart("imagenes") MultipartFile[] imagenes) {

		try {
			
			 if (imagenes != null && imagenes.length==0) {
			for (int i = 0; i < imagenes.length; i++) {
				// Guardar imagen
				String rutaRelativa = imageStorageService.saveImage(imagenes[i], "ejercicios");
				ejercicioDTO.getImagenes().get(i).setUrlImagen(rutaRelativa);

			}}
			// Mapear el DTO a la entidad
			Ejercicio ejercicio = ejercicioMapper.toEjercicio(ejercicioDTO);

			// Guardar el ejercicio con sus imágenes
			ejercicioBusiness.saveEjercicio(ejercicio);
			// Retornar la respuesta
			return new ResponseEntity<>(ejercicioMapper.toDto(ejercicio), HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace(); // Esto ayuda a ver la causa en consola
			return ResponseEntity.internalServerError().build();
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<EjercicioDTO> getEjercicioById(@PathVariable int id) {
		Ejercicio ejercicio = ejercicioBusiness.getEjercicioById(id);
		return new ResponseEntity<>(ejercicioMapper.toDto(ejercicio), HttpStatus.OK);
	}

	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<EjercicioDTO> getEjercicioByName(@PathVariable String nombre) {
		Ejercicio ejercicio = ejercicioBusiness.getEjercicioByName(nombre);
		if (ejercicio == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ejercicioMapper.toDto(ejercicio), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<EjercicioDTO>> getAllEjercicios() {
		List<Ejercicio> ejercicios = ejercicioBusiness.getAllEjercicios();
		return new ResponseEntity<>(ejercicios.stream().map(ejercicioMapper::toDto).toList(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EjercicioDTO> updateEjercicio(@PathVariable int id,
			@RequestPart("ejercicio") EjercicioDTO ejercicioDTO, @RequestPart(value ="imagenes", required = false) MultipartFile[] imagenes) {

		try {
		    

			 if (imagenes != null && imagenes.length>0) {
				
			for (int i = 0; i < imagenes.length; i++) {
				// Guardar imagen
				String rutaRelativa = imageStorageService.saveImage(imagenes[i], "ejercicios");
				 System.out.print(rutaRelativa);
				ejercicioDTO.getImagenes().get(i).setUrlImagen(rutaRelativa);

			}}
			// Mapear el DTO a la entidad
			Ejercicio ejercicio = ejercicioMapper.toEjercicio(ejercicioDTO);
			ejercicio.setIdEjercicio(id);

			// Guardar el ejercicio con sus imágenes
			ejercicioBusiness.updateEjercicio(ejercicio);
			// Retornar la respuesta
			return new ResponseEntity<>(ejercicioMapper.toDto(ejercicio), HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace(); // Esto ayuda a ver la causa en consola
			return ResponseEntity.internalServerError().build();
		}
		
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEjercicio(@PathVariable int id) {
		ejercicioBusiness.deleteEjercicio(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}/imagenes")
	public ResponseEntity<List<Map<String, Object>>> getImagenesByEjercicioId(@PathVariable int id) {
		List<Map<String, Object>> imagenes = ejercicioBusiness.getImagenesByEjercicioId(id);
		return new ResponseEntity<>(imagenes, HttpStatus.OK);
	}
}