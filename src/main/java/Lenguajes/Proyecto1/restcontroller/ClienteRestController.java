package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.ClienteBusiness;
import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import Lenguajes.Proyecto1.domain.Cliente;
import Lenguajes.Proyecto1.dto.CategoriaEjercicioDTO;
import Lenguajes.Proyecto1.dto.ClienteDTO;
import Lenguajes.Proyecto1.mapper.ClienteMapper;
import Lenguajes.Proyecto1.service.ImageStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clientes")
public class ClienteRestController {
	@Autowired
    private  ClienteBusiness clienteBusiness;
    @Autowired
    private ImageStorageService imageStorageService;
    @Autowired
    private  ClienteMapper clienteMapper;
  

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerTodosLosClientes() {
    	 List<Cliente>  clientes = clienteBusiness.obtenerTodosLosClientes( );
 
         return new ResponseEntity<>(clientes.stream().map(clienteMapper::toDTO).toList(), HttpStatus.OK);

    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable int id) {
      
        Cliente cliente = clienteBusiness.obtenerClientePorId(id);
        return new ResponseEntity<>(clienteMapper.toDTO(cliente), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  ResponseEntity<ClienteDTO>  insertarCliente(
            @RequestPart("cliente") ClienteDTO clienteDTO,
            @RequestPart("imagen") MultipartFile imagen) {
    	  
        try {
            // Guardar imagen
        	String rutaRelativa = imageStorageService.saveImage(imagen, "clientes");

            // Asociar imagen al DTO
        	clienteDTO.setFotografia(rutaRelativa);

            // Convertir y guardar
        	Cliente cliente= clienteMapper.toEntity(clienteDTO);
         
        	 clienteBusiness.insertarCliente(cliente);

            return new ResponseEntity<>(clienteMapper.toDTO(cliente), HttpStatus.CREATED);
        } catch (IOException e) {
        	  e.printStackTrace(); // Esto ayuda a ver la causa en consola
        	    return ResponseEntity.internalServerError().build();
        }
    

    	
    	
    	
       
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable int id,  @RequestPart("cliente") ClienteDTO clienteDTO,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
    	
    	
    	
        clienteDTO.setIdCliente(id);
      
        
        try {
        	if (imagen != null && !imagen.isEmpty()) {
            // Guardar imagen
        	String rutaRelativa = imageStorageService.saveImage(imagen, "clientes");

            // Asociar imagen al DTO
        	clienteDTO.setFotografia(rutaRelativa);
        	}
            // Convertir y guardar
        	Cliente cliente= clienteMapper.toEntity(clienteDTO);
         
        	 clienteBusiness.actualizarCliente(cliente);

            return new ResponseEntity<>(clienteMapper.toDTO(cliente), HttpStatus.CREATED);
        } catch (IOException e) {
        	  e.printStackTrace(); // Esto ayuda a ver la causa en consola
        	    return ResponseEntity.internalServerError().build();
        }
        
        
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable int id) {
        clienteBusiness.eliminarCliente(id);
    }
}
