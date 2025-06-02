package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.ClienteData;
import Lenguajes.Proyecto1.domain.Cliente;
import Lenguajes.Proyecto1.dto.ClienteDTO;
import Lenguajes.Proyecto1.mapper.ClienteMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteBusiness {
    @Autowired
    private  ClienteData clienteData;
   

  

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteData.obtenerTodosLosClientes();
        return clientes;
    }

    public Cliente obtenerClientePorId(int idCliente) {
        Cliente cliente = clienteData.obtenerClientePorId(idCliente);
        return cliente;
    }

    public void insertarCliente(Cliente cliente) {
       
        clienteData.insertarCliente(cliente);
    }

    public void actualizarCliente(Cliente cliente) {
        clienteData.actualizarCliente(cliente);
    }

    public void eliminarCliente(int idCliente) {
        clienteData.eliminarCliente(idCliente);
    }
}
