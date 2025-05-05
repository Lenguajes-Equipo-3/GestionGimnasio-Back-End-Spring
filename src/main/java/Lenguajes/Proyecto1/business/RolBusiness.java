package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.RolData;
import Lenguajes.Proyecto1.domain.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolBusiness {

    @Autowired
    private RolData rolData;

    @Transactional(readOnly = true) // Transacción de solo lectura
    public List<Rol> getAllRoles() {
        return rolData.findAll();
    }

}