package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.Rutina;
import Lenguajes.Proyecto1.dto.RutinaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {
        EmpleadoMapper.class,
        ClienteMapper.class,
        ItemRutinaEjercicioMapper.class,
        ItemRutinaMedidaMapper.class
})
public interface RutinaMapper {

    @Mappings({
            @Mapping(source = "empleado", target = "empleado"),
            @Mapping(source = "cliente", target = "cliente"),
            @Mapping(source = "ejercicios", target = "ejercicios"),
            @Mapping(source = "medidas", target = "medidas")
    })
    RutinaDTO toDto(Rutina rutina);

    @Mappings({
            @Mapping(source = "empleado", target = "empleado"),
            @Mapping(source = "cliente", target = "cliente"),
            @Mapping(source = "ejercicios", target = "ejercicios"),
            @Mapping(source = "medidas", target = "medidas")
    })
    Rutina toRutina(RutinaDTO rutinaDTO);
}