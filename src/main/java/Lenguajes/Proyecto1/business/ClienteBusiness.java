package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.ClienteData;
import Lenguajes.Proyecto1.domain.Cliente;
import Lenguajes.Proyecto1.dto.ClienteDTO;
import Lenguajes.Proyecto1.mapper.ClienteMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteBusiness {

    private final ClienteData clienteData;
    private final ClienteMapper clienteMapper;

    public ClienteBusiness(ClienteData clienteData, ClienteMapper clienteMapper) {
        this.clienteData = clienteData;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteDTO> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteData.obtenerTodosLosClientes();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO obtenerClientePorId(int idCliente) {
        Cliente cliente = clienteData.obtenerClientePorId(idCliente);
        return clienteMapper.toDTO(cliente);
    }

    public void insertarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        clienteData.insertarCliente(cliente);
    }

    public void actualizarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        clienteData.actualizarCliente(cliente);
    }

    public void eliminarCliente(int idCliente) {
        clienteData.eliminarCliente(idCliente);
    }
}
