package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.ItemRutinaEjercicio;
import Lenguajes.Proyecto1.dto.ItemRutinaEjercicioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EjercicioMapper.class})
public interface ItemRutinaEjercicioMapper {

    @Mapping(source = "ejercicio", target = "ejercicio")
    @Mapping(target = "rutina", ignore = true) // Rompe el ciclo
    ItemRutinaEjercicioDTO toDto(ItemRutinaEjercicio entity);

    @Mapping(source = "ejercicio", target = "ejercicio")
    @Mapping(target = "rutina", ignore = true) // Rompe el ciclo
    ItemRutinaEjercicio toEntity(ItemRutinaEjercicioDTO dto);

    List<ItemRutinaEjercicioDTO> toDtoList(List<ItemRutinaEjercicio> entities);
    List<ItemRutinaEjercicio> toEntityList(List<ItemRutinaEjercicioDTO> dtos);
}