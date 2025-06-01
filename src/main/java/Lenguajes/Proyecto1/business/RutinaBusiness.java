package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.RutinaData;
import Lenguajes.Proyecto1.domain.Rutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}
