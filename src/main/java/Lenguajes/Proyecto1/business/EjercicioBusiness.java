package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.EjercicioData;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.ImagenEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EjercicioBusiness {

    @Autowired
    private EjercicioData ejercicioData;

    public void saveEjercicio(Ejercicio ejercicio) {
        // Validar los datos del ejercicio
        if (ejercicio.getNombreEjercicio() == null || ejercicio.getNombreEjercicio().isEmpty()) {
            throw new IllegalArgumentException("El nombre del ejercicio no puede estar vacío");
        }
        if (ejercicio.getDescripcionEjercicio() == null || ejercicio.getDescripcionEjercicio().isEmpty()) {
            throw new IllegalArgumentException("La descripción del ejercicio no puede estar vacía");
        }
        if (ejercicio.getCodigoEquipo() == null || ejercicio.getCodigoEquipo().isEmpty()) {
            throw new IllegalArgumentException("El código del equipo no puede estar vacío");
        }

        for (ImagenEjercicio imagen : ejercicio.getImagenes()) {
            if (imagen.getUrlImagen() == null || imagen.getUrlImagen().isEmpty()) {
                throw new IllegalArgumentException("La URL de la imagen no puede estar vacía");
            }
        }

        // Guardar el ejercicio
        ejercicioData.save(ejercicio);
    }

    public Ejercicio getEjercicioById(int idEjercicio) {
        // Validar el ID del ejercicio
        if (idEjercicio <= 0) {
            throw new IllegalArgumentException("El ID del ejercicio no es válido");
        }

        // Obtener el ejercicio de la base de datos
        return ejercicioData.findById(idEjercicio);

    }

    public void updateEjercicio(Ejercicio ejercicio) {
        // Validar los datos del ejercicio
        if (ejercicio.getIdEjercicio() <= 0) {
            throw new IllegalArgumentException("El ID del ejercicio no es válido");
        }
        if (ejercicio.getNombreEjercicio() == null || ejercicio.getNombreEjercicio().isEmpty()) {
            throw new IllegalArgumentException("El nombre del ejercicio no puede estar vacío");
        }
        if (ejercicio.getDescripcionEjercicio() == null || ejercicio.getDescripcionEjercicio().isEmpty()) {
            throw new IllegalArgumentException("La descripción del ejercicio no puede estar vacía");
        }
        if (ejercicio.getCodigoEquipo() == null || ejercicio.getCodigoEquipo().isEmpty()) {
            throw new IllegalArgumentException("El código del equipo no puede estar vacío");
        }

        // Actualizar el ejercicio en la base de datos
        ejercicioData.update(ejercicio);
    }

    public void deleteEjercicio(int idEjercicio) {
        // Validar el ID del ejercicio
        if (idEjercicio <= 0) {
            throw new IllegalArgumentException("El ID del ejercicio no es válido");
        }

        // Eliminar el ejercicio de la base de datos
        ejercicioData.delete(idEjercicio);
    }

    public List<Ejercicio> getAllEjercicios() {
        // Obtener todos los ejercicios de la base de datos
        return ejercicioData.findAll();
    }

    public List<Map<String, Object>> getImagenesByEjercicioId(int idEjercicio) {
        if (idEjercicio <= 0) {
            throw new IllegalArgumentException("El ID del ejercicio no es válido");
        }
        return ejercicioData.getImagenesByEjercicioId(idEjercicio);
    }

}
