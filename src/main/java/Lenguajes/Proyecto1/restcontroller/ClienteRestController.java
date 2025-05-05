package Lenguajes.Proyecto1.restController;

import Lenguajes.Proyecto1.business.ClienteBusiness;
import Lenguajes.Proyecto1.dto.ClienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteBusiness clienteBusiness;

    public ClienteRestController(ClienteBusiness clienteBusiness) {
        this.clienteBusiness = clienteBusiness;
    }//cliente Business

    @GetMapping
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteBusiness.obtenerTodosLosClientes();
    }

    @GetMapping("/{id}")
    public ClienteDTO obtenerClientePorId(@PathVariable int id) {
        return clienteBusiness.obtenerClientePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertarCliente(@RequestBody ClienteDTO clienteDTO) {
        clienteBusiness.insertarCliente(clienteDTO);
    }

    @PutMapping("/{id}")
    public void actualizarCliente(@PathVariable int id, @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setIdCliente(id);
        clienteBusiness.actualizarCliente(clienteDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable int id) {
        clienteBusiness.eliminarCliente(id);
    }
}
