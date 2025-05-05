package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.Rol;
import Lenguajes.Proyecto1.dto.RolDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

    @Mapping(source = "rolId", target = "rolId")
    @Mapping(source = "nombreRol", target = "nombreRol")
    RolDTO toDto(Rol rol);

    @Mapping(source = "rolId", target = "rolId")
    @Mapping(source = "nombreRol", target = "nombreRol")
    Rol toEntity(RolDTO rolDTO);

    // Mapeo para listas
    List<RolDTO> toDtoList(List<Rol> roles);
    List<Rol> toEntityList(List<RolDTO> rolDTOs);
}