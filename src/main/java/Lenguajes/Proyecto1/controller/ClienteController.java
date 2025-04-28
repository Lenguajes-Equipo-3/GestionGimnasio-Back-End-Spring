package Lenguajes.Proyecto1.controller;

import Lenguajes.Proyecto1.business.ClienteBusiness;
import Lenguajes.Proyecto1.dto.ClienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteBusiness clienteBusiness;

    public ClienteController(ClienteBusiness clienteBusiness) {
        this.clienteBusiness = clienteBusiness;
    }

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
