package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.RutinaData;
import Lenguajes.Proyecto1.domain.Ejercicio;
import Lenguajes.Proyecto1.domain.Rutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaBusiness {

    @Autowired
    private RutinaData rutinaData;

    public RutinaBusiness(RutinaData rutinaData) {
        this.rutinaData = rutinaData;
    }

    public void saveRutina(Rutina rutina) {
        if (rutina == null) {
            throw new IllegalArgumentException("La rutina no puede ser nula");
        }
        rutinaData.save(rutina);
    }

    public List<Rutina> getAllRutinas() {
        return rutinaData.findAll();
    }

    public Rutina getRutinaById(int idRutina) {
        if (idRutina <= 0) {
            throw new IllegalArgumentException("El ID de la rutina no es válido");
        }
        return rutinaData.findById(idRutina);
    }

    public Rutina getRutinaByEmpleadoId(int idEmpleado) {
        if (idEmpleado <= 0) {
            throw new IllegalArgumentException("El ID del empleado no es válido");
        }
        return rutinaData.findRutinaByEmpleadoId(idEmpleado);
    }

    public Rutina getRutinaByClienteId(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID del cliente no es válido");
        }
        return rutinaData.findRutinaByClienteId(idCliente);
    }

    public void updateRutina(Rutina rutina) {
        if (rutina == null || rutina.getIdRutina() <= 0) {
            throw new IllegalArgumentException("La rutina o su ID no son válidos");
        }
        rutinaData.update(rutina);
    }

    public void deleteRutina(int idRutina) {
        if (idRutina <= 0) {
            throw new IllegalArgumentException("El ID de la rutina no es válido");
        }
        rutinaData.delete(idRutina);
    }


}
