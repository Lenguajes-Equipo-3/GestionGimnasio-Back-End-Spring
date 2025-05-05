package Lenguajes.Proyecto1.business;

import Lenguajes.Proyecto1.data.EmpleadoData;
import Lenguajes.Proyecto1.domain.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoBusiness {

	@Autowired
	private EmpleadoData empleadoData;

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

		// TODO: Hacer el hashing de la contraseña.
		String passwordToStore = plainPassword;

		return empleadoData.save(empleado, passwordToStore);
	}

	public Empleado getEmpleadoById(int idEmpleado) {
		if (idEmpleado <= 0) {
			throw new IllegalArgumentException("El ID del empleado no es válido.");
		}
		Optional<Empleado> empleadoOpt = empleadoData.findById(idEmpleado);
		return empleadoOpt.orElseThrow(() -> new RuntimeException("Empleado con ID " + idEmpleado + " no encontrado."));
	}

	public List<Empleado> getAllEmpleados() {
		return empleadoData.findAll();
	}

	public void updateEmpleado(Empleado empleadoFromUpdateDTO, String passwordFromUpdateDTO) {
		if (empleadoFromUpdateDTO == null || empleadoFromUpdateDTO.getIdEmpleado() <= 0) {
			throw new IllegalArgumentException("Datos de empleado para actualizar no válidos.");
		}
		if (empleadoFromUpdateDTO.getRolId() <= 0) {
			throw new IllegalArgumentException("El ID de rol no es válido.");
		}
		// Validar tamaño de contraseña SÓLO SI se proporciona una
		if (passwordFromUpdateDTO != null && !passwordFromUpdateDTO.trim().isEmpty()
				&& (passwordFromUpdateDTO.length() < 4 || passwordFromUpdateDTO.length() > 50)) {
			throw new IllegalArgumentException("La contraseña proporcionada debe tener entre 4 y 50 caracteres.");
		}

		// Verifica si el empleado existe ANTES de intentar actualizar
		getEmpleadoById(empleadoFromUpdateDTO.getIdEmpleado());

		// Determinar qué pasar a la capa de datos para la contraseña
		String passwordToStore;
		if (passwordFromUpdateDTO == null || passwordFromUpdateDTO.trim().isEmpty()) {
			// No se proporcionó nueva contraseña -> pasar NULL al SP (modificado)
			passwordToStore = null;
		} else {
			//TODO Hacer el hashing de la contraseña
			passwordToStore = passwordFromUpdateDTO.trim();
		}

		empleadoData.update(empleadoFromUpdateDTO, passwordToStore);
	}

	public void deleteEmpleado(int idEmpleado) {
		if (idEmpleado <= 0) {
			throw new IllegalArgumentException("El ID del empleado a eliminar no es válido.");
		}
		getEmpleadoById(idEmpleado);
		empleadoData.delete(idEmpleado);
	}
}