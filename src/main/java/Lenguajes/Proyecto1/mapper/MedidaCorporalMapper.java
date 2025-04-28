package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.MedidaCorporal;
import Lenguajes.Proyecto1.dto.MedidaCorporalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MedidaCorporalMapper {

    MedidaCorporalMapper INSTANCE = Mappers.getMapper(MedidaCorporalMapper.class);

    @Mapping(source = "idMedida", target = "idMedida")
    @Mapping(source = "nombreMedida", target = "nombreMedida")
    @Mapping(source = "unidadMedida", target = "unidadMedida")
    @Mapping(source = "esObligatoria", target = "esObligatoria")
    @Mapping(source = "idCategoria", target = "idCategoria")
    MedidaCorporalDTO toDto(MedidaCorporal medidaCorporal);

    @Mapping(source = "idMedida", target = "idMedida")
    @Mapping(source = "nombreMedida", target = "nombreMedida")
    @Mapping(source = "unidadMedida", target = "unidadMedida")
    @Mapping(source = "esObligatoria", target = "esObligatoria")
    @Mapping(source = "idCategoria", target = "idCategoria")
    MedidaCorporal toEntity(MedidaCorporalDTO medidaCorporalDTO);
}
