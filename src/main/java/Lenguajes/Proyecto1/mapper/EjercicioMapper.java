package Lenguajes.Proyecto1.mapper;

import Lenguajes.Proyecto1.domain.CategoriaEjercicio;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.ImagenEjercicio;
import Lenguajes.Proyecto1.dto.CategoriaEjercicioDTO;
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
            @Mapping(target = "categoriaEjercicio", expression = "java(mapCategoriaEjercicio(ejercicioDTO.getCategoriaEjercicio()))"),
            @Mapping(target = "imagenes", expression = "java(mapImagenes(ejercicioDTO.getImagenes()))")
    })
    Ejercicio toEjercicio(EjercicioDTO ejercicioDTO);

    @Mappings({
            @Mapping(target = "categoriaEjercicio", expression = "java(mapCategoriaEjercicioDTO(ejercicio.getCategoriaEjercicio()))"),
            @Mapping(target = "imagenes", expression = "java(mapImagenesDTO(ejercicio.getImagenes()))")
    })
    EjercicioDTO toDto(Ejercicio ejercicio);

    default CategoriaEjercicio mapCategoriaEjercicio(CategoriaEjercicioDTO categoriaEjercicioDTO) {
        if (categoriaEjercicioDTO == null) {
            return null;
        }
        CategoriaEjercicio categoriaEjercicio = new CategoriaEjercicio();
        categoriaEjercicio.setIdCategoria(categoriaEjercicioDTO.getIdCategoria());
        categoriaEjercicio.setNombreCategoria(categoriaEjercicioDTO.getNombreCategoria());
        categoriaEjercicio.setImagen(categoriaEjercicioDTO.getImagen());
        return categoriaEjercicio;
    }

    default CategoriaEjercicioDTO mapCategoriaEjercicioDTO(CategoriaEjercicio categoriaEjercicio) {
        if (categoriaEjercicio == null) {
            return null;
        }
        CategoriaEjercicioDTO categoriaEjercicioDTO = new CategoriaEjercicioDTO();
        categoriaEjercicioDTO.setIdCategoria(categoriaEjercicio.getIdCategoria());
        categoriaEjercicioDTO.setNombreCategoria(categoriaEjercicio.getNombreCategoria());
        categoriaEjercicioDTO.setImagen(categoriaEjercicio.getImagen());
        return categoriaEjercicioDTO;
    }

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