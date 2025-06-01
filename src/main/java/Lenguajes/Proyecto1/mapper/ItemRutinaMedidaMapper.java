package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.ItemRutinaMedida;
import Lenguajes.Proyecto1.dto.ItemRutinaMedidaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {MedidaCorporalMapper.class})
public interface ItemRutinaMedidaMapper {

    @Mapping(source = "medidaCorporal", target = "medidaCorporal")
    @Mapping(target = "rutina", ignore = true)
    ItemRutinaMedidaDTO toDto(ItemRutinaMedida entity);

    @Mapping(source = "medidaCorporal", target = "medidaCorporal")
    @Mapping(target = "rutina", ignore = true)
    ItemRutinaMedida toEntity(ItemRutinaMedidaDTO dto);

    List<ItemRutinaMedidaDTO> toDtoList(List<ItemRutinaMedida> entities);
    List<ItemRutinaMedida> toEntityList(List<ItemRutinaMedidaDTO> dtos);
}