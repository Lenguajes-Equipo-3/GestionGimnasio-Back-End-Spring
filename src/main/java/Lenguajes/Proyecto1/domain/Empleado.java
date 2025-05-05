package Lenguajes.Proyecto1.domain;

public class Empleado extends Usuario {

	private int idEmpleado;
	private String nombreEmpleado;
	private String apellidosEmpleado;
	private int rolId;
	private String nombreRol;

	public Empleado() {
		super();
		this.idEmpleado = -1;
		this.nombreEmpleado = "";
		this.apellidosEmpleado = "";
		this.rolId = -1;
		this.nombreRol = "";
	}

	public Empleado(int idUsuario, String nombreUsuario, String contrasena, int idEmpleado, String nombreEmpleado,
			String apellidosEmpleado, int rolId, String nombreRol) {
		super(idUsuario, nombreUsuario, contrasena);
		this.idEmpleado = idEmpleado;
		this.nombreEmpleado = nombreEmpleado;
		this.apellidosEmpleado = apellidosEmpleado;
		this.rolId = rolId;
		this.nombreRol = nombreRol;
	}

	public int getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombreEmpleado() {
		return this.nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getApellidosEmpleado() {
		return this.apellidosEmpleado;
	}

	public void setApellidosEmpleado(String apellidosEmpleado) {
		this.apellidosEmpleado = apellidosEmpleado;
	}

	public int getRolId() {
		return this.rolId;
	}

	public void setRolId(int rolId) {
		this.rolId = rolId;
	}

	public String getNombreRol() {
		return this.nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

}// Empleado