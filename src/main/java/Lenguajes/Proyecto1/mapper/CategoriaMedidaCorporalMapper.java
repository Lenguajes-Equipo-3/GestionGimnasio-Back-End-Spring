package Lenguajes.Proyecto1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import Lenguajes.Proyecto1.domain.CategoriaMedidaCorporal;
import Lenguajes.Proyecto1.dto.CategoriaMedidaCorporalDTO;

@Mapper(componentModel = "spring")
public interface CategoriaMedidaCorporalMapper {

    CategoriaMedidaCorporalMapper INSTANCE = Mappers.getMapper(CategoriaMedidaCorporalMapper.class);

    @Mapping(source = "idCategoria", target = "idCategoria")
    @Mapping(source = "nombreCategoria", target = "nombreCategoria")
    CategoriaMedidaCorporalDTO toDto(CategoriaMedidaCorporal categoriaMedidaCorporal);

    @Mapping(source = "idCategoria", target = "idCategoria")
    @Mapping(source = "nombreCategoria", target = "nombreCategoria")
    CategoriaMedidaCorporal toEntity(CategoriaMedidaCorporalDTO categoriaMedidaCorporalDTO);
}
