package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.dto.EjercicioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EjercicioMapper {

    EjercicioMapper INSTANCE = Mappers.getMapper(EjercicioMapper.class);

    @Mapping(source = "idEjercicio", target = "idEjercicio")
    @Mapping(source = "idCategoriaEjercicio", target = "idCategoriaEjercicio")
    @Mapping(source = "nombreEjercicio", target = "nombreEjercicio")
    @Mapping(source = "descripcionEjercicio", target = "descripcionEjercicio")
    @Mapping(source = "codigoEquipo", target = "codigoEquipo")
    EjercicioDTO toDto(Ejercicio ejercicio);

    @Mapping(source = "idEjercicio", target = "idEjercicio")
    @Mapping(source = "idCategoriaEjercicio", target = "idCategoriaEjercicio")
    @Mapping(source = "nombreEjercicio", target = "nombreEjercicio")
    @Mapping(source = "descripcionEjercicio", target = "descripcionEjercicio")
    @Mapping(source = "codigoEquipo", target = "codigoEquipo")
    Ejercicio toEntity(EjercicioDTO ejercicioDTO);


}
