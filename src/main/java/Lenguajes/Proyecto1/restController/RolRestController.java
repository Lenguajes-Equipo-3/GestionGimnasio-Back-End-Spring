package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.RolBusiness;
import Lenguajes.Proyecto1.domain.Rol;
import Lenguajes.Proyecto1.dto.RolDTO;
import Lenguajes.Proyecto1.mapper.RolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/roles") // Ruta base para los endpoints de roles
@CrossOrigin(origins = "http://localhost:4200") // Permite CORS
public class RolRestController {

	@Autowired
	private RolBusiness rolBusiness;

	@Autowired
	private RolMapper rolMapper;

	// GET /api/roles
	@GetMapping
	public ResponseEntity<List<RolDTO>> getAllRoles() {
		try {
			List<Rol> roles = rolBusiness.getAllRoles();
			List<RolDTO> rolDTOs = rolMapper.toDtoList(roles);

			if (rolDTOs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(rolDTOs, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error al obtener roles: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al obtener los roles",
					e);
		}
	}

}