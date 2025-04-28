package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.MedidaCorporalData;
import Lenguajes.Proyecto1.domain.MedidaCorporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedidaCorporalBusiness {

    @Autowired
    private MedidaCorporalData medidaCorporalData;

    public void saveMedidaCorporal(MedidaCorporal medida) throws Exception {
        validarMedida(medida);
        medidaCorporalData.save(medida);
    }

    public MedidaCorporal getMedidaCorporalById(int idMedida) {
        if (idMedida <= 0) {
            throw new IllegalArgumentException("El ID de la medida no es válido.");
        }
        // Obtener la medida desde la base de datos
        return medidaCorporalData.findById(idMedida);
    }

    public List<MedidaCorporal> getAllMedidasCorporales() {
        return medidaCorporalData.findAll();
    }

    public void updateMedidaCorporal(MedidaCorporal medida) {
        if (medida.getIdMedida() <= 0) {
            throw new IllegalArgumentException("El ID de la medida es inválido.");
        }
        validarMedida(medida);

        medidaCorporalData.update(medida);
    }

    public void deleteMedidaCorporal(int idMedida) {
        if (idMedida <= 0) {
            throw new IllegalArgumentException("El ID de la medida no es válido.");
        }

        medidaCorporalData.delete(idMedida);
    }

    private void validarMedida(MedidaCorporal medida) {
        if (medida.getNombreMedida() == null || medida.getNombreMedida().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la medida no puede estar vacío.");
        }
        if (medida.getUnidadMedida() == null || medida.getUnidadMedida().isEmpty()) {
            throw new IllegalArgumentException("La unidad de medida no puede estar vacía.");
        }

    }
}

