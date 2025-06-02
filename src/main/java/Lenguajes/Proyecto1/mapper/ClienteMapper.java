package Lenguajes.Proyecto1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import Lenguajes.Proyecto1.domain.Cliente;
import Lenguajes.Proyecto1.dto.ClienteDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    // Mapea entidad a DTO
    @Mapping(source = "idCliente", target = "idCliente")
    @Mapping(source = "numeroIdentificacion", target = "numeroIdentificacion")
    @Mapping(source = "nombreCliente", target = "nombreCliente")
    @Mapping(source = "apellidosCliente", target = "apellidosCliente")
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "nombreContactoEmergencia", target = "nombreContactoEmergencia")
    @Mapping(source = "telefonoContactoEmergencia", target = "telefonoContactoEmergencia")
    @Mapping(source = "fotografia", target = "fotografia")
    @Mapping(source = "gmail", target = "gmail")
    ClienteDTO toDTO(Cliente cliente);

    // Mapea DTO a entidad
    @Mapping(source = "idCliente", target = "idCliente")
    @Mapping(source = "numeroIdentificacion", target = "numeroIdentificacion")
    @Mapping(source = "nombreCliente", target = "nombreCliente")
    @Mapping(source = "apellidosCliente", target = "apellidosCliente")
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "nombreContactoEmergencia", target = "nombreContactoEmergencia")
    @Mapping(source = "telefonoContactoEmergencia", target = "telefonoContactoEmergencia")
    @Mapping(source = "fotografia", target = "fotografia")
    @Mapping(source = "gmail", target = "gmail")
    Cliente toEntity(ClienteDTO clienteDTO);
}
