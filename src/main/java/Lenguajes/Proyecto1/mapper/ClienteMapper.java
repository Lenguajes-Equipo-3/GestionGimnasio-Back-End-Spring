package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.Cliente;
import Lenguajes.Proyecto1.dto.ClienteDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getNumeroIdentificacion(),
                cliente.getNombreCliente(),
                cliente.getApellidosCliente(),
                cliente.getFechaNacimiento(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getNombreContactoEmergencia(),
                cliente.getTelefonoContactoEmergencia(),
                cliente.getFotografia()
        );
    }

    public Cliente toEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(clienteDTO.getIdCliente());
        cliente.setNumeroIdentificacion(clienteDTO.getNumeroIdentificacion());
        cliente.setNombreCliente(clienteDTO.getNombreCliente());
        cliente.setApellidosCliente(clienteDTO.getApellidosCliente());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setNombreContactoEmergencia(clienteDTO.getNombreContactoEmergencia());
        cliente.setTelefonoContactoEmergencia(clienteDTO.getTelefonoContactoEmergencia());
        cliente.setFotografia(clienteDTO.getFotografia());
        return cliente;
    }
}
