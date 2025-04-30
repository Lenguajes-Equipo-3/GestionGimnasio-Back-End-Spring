package Lenguajes.Proyecto1.domain;

public abstract class Usuario {
	
	private int idUsuario;
	protected String nombreUsuario;
	protected String contrasena;
	
	public Usuario() {
		this.idUsuario = -1;
		this.nombreUsuario = "";
		this.contrasena = "";
	}

	public Usuario(int idUsuario, String nombre, String contrasena) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombre;
		this.contrasena = contrasena;
	}
	
	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
}//Usuario