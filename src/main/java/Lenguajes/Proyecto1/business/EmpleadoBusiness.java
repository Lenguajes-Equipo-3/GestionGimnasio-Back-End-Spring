package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.EmpleadoData;
import Lenguajes.Proyecto1.domain.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoBusiness {

    @Autowired
    private EmpleadoData empleadoData;


    @Transactional
    public Empleado saveEmpleado(Empleado empleado, String plainPassword) {
        if (empleado == null) {
            throw new IllegalArgumentException("El objeto Empleado no puede ser nulo.");
        }
        if (empleado.getRolId() <= 0) {
             throw new IllegalArgumentException("El ID de rol no es válido.");
        }
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }
        
        // TODO: Hacer el hashing de la contrasena.
        String passwordToStore = plainPassword;

        return empleadoData.save(empleado, passwordToStore);
    }

    @Transactional(readOnly = true)
    public Empleado getEmpleadoById(int idEmpleado) {
        if (idEmpleado <= 0) {
            throw new IllegalArgumentException("El ID del empleado no es válido.");
        }
        Optional<Empleado> empleadoOpt = empleadoData.findById(idEmpleado);
        return empleadoOpt.orElseThrow(() -> new RuntimeException("Empleado con ID " + idEmpleado + " no encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Empleado> getAllEmpleados() {
        return empleadoData.findAll();
    }

    @Transactional
    public void updateEmpleado(Empleado empleado, String plainPassword) {
         if (empleado == null || empleado.getIdEmpleado() <= 0) {
            throw new IllegalArgumentException("El ID del empleado a actualizar no es válido.");
        }
         if (empleado.getRolId() <= 0) {
             throw new IllegalArgumentException("El ID de rol no es válido.");
         }

         getEmpleadoById(empleado.getIdEmpleado()); 
         
         //TODO: Hacer el hashing de la contrasena

         if (plainPassword == null || plainPassword.trim().isEmpty()) {
             throw new IllegalArgumentException("La contraseña es requerida para la actualización.");
         }
         String passwordToStore = plainPassword;

         empleadoData.update(empleado, passwordToStore);
    }

    @Transactional
    public void deleteEmpleado(int idEmpleado) {
        if (idEmpleado <= 0) {
            throw new IllegalArgumentException("El ID del empleado a eliminar no es válido.");
        }
        getEmpleadoById(idEmpleado);
        empleadoData.delete(idEmpleado);
    }
}