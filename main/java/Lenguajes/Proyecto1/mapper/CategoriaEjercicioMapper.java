package Lenguajes.Proyecto1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import Lenguajes.Proyecto1.dto.CategoriaEjercicioDTO;

@Mapper(componentModel = "spring")
public interface CategoriaEjercicioMapper {

    CategoriaEjercicioMapper INSTANCE = Mappers.getMapper(CategoriaEjercicioMapper.class);


    @Mapping(source = "idCategoriaEjercicio", target = "idCategoriaEjercicio")
    @Mapping(source = "nombreCategoria", target = "nombreCategoria")
    @Mapping(source = "imagen", target = "imagen")
    CategoriaEjercicioDTO toDto(CategoriaEjercicio categoriaEjercicio);

    @Mapping(source = "idCategoriaEjercicio", target = "idCategoriaEjercicio")
    @Mapping(source = "nombreCategoria", target = "nombreCategoria")
    @Mapping(source = "imagen", target = "imagen")
    CategoriaEjercicio toEntity(CategoriaEjercicioDTO categoriaEjercicioDTO);
}
