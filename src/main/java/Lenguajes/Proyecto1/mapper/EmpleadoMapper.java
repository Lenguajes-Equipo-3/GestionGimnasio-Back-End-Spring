package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.Empleado;
import Lenguajes.Proyecto1.dto.EmpleadoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings; 
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    EmpleadoMapper INSTANCE = Mappers.getMapper(EmpleadoMapper.class);

    @Mappings({
        @Mapping(source = "idEmpleado", target = "idEmpleado"),
        @Mapping(source = "nombreEmpleado", target = "nombreEmpleado"),
        @Mapping(source = "apellidosEmpleado", target = "apellidosEmpleado"),
        @Mapping(source = "idUsuario", target = "idUsuario"),
        @Mapping(source = "nombreUsuario", target = "nombreUsuario"), 
        @Mapping(source = "rolId", target = "rolId"),
        @Mapping(source = "nombreRol", target = "nombreRol"),
        @Mapping(target = "contrasena", ignore = true) // Ignora la contraseña al enviar al cliente
    })
    EmpleadoDTO toDto(Empleado empleado);

    @Mappings({
        @Mapping(source = "idEmpleado", target = "idEmpleado"),
        @Mapping(source = "nombreEmpleado", target = "nombreEmpleado"),
        @Mapping(source = "apellidosEmpleado", target = "apellidosEmpleado"),
        @Mapping(source = "idUsuario", target = "idUsuario"),
        @Mapping(source = "nombreUsuario", target = "nombreUsuario"),
        @Mapping(source = "rolId", target = "rolId"),
        @Mapping(source = "nombreRol", target = "nombreRol"),
        @Mapping(source = "contrasena", target = "contrasena")
    })
    Empleado toEntity(EmpleadoDTO empleadoDTO);

    List<EmpleadoDTO> toDtoList(List<Empleado> empleados);
    List<Empleado> toEntityList(List<EmpleadoDTO> empleadoDTOs);
}