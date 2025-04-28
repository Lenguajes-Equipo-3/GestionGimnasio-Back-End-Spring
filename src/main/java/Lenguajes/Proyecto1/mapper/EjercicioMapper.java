package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.ImagenEjercicio;
import Lenguajes.Proyecto1.dto.EjercicioDTO;
import Lenguajes.Proyecto1.dto.ImagenEjercicioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EjercicioMapper {

    @Mappings({
            @Mapping(target = "imagenes", expression = "java(mapImagenes(ejercicioDTO.getImagenes()))")
    })
    Ejercicio toEjercicio(EjercicioDTO ejercicioDTO);

    @Mappings({
            @Mapping(target = "imagenes", expression = "java(mapImagenesDTO(ejercicio.getImagenes()))")
    })
    EjercicioDTO toDto(Ejercicio ejercicio);

    default List<ImagenEjercicio> mapImagenes(List<ImagenEjercicioDTO> imagenesDTO) {
        if (imagenesDTO == null) {
            return null;
        }
        return imagenesDTO.stream().map(imagenDTO -> {
            ImagenEjercicio imagen = new ImagenEjercicio();
            imagen.setUrlImagen(imagenDTO.getUrlImagen());
            imagen.setDescripcionImagen(imagenDTO.getDescripcionImagen());
            return imagen;
        }).collect(Collectors.toList());
    }

    default List<ImagenEjercicioDTO> mapImagenesDTO(List<ImagenEjercicio> imagenes) {
        if (imagenes == null) {
            return null;
        }
        return imagenes.stream().map(imagen -> {
            ImagenEjercicioDTO imagenDTO = new ImagenEjercicioDTO();
            imagenDTO.setUrlImagen(imagen.getUrlImagen());
            imagenDTO.setDescripcionImagen(imagen.getDescripcionImagen());
            return imagenDTO;
        }).collect(Collectors.toList());
    }
}