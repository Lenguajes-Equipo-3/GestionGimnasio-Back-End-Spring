package Lenguajes.Proyecto1.domain;

public class Rol {
    private int rolId;
    private String nombreRol;

    public Rol() {
    }

    public Rol(int rolId, String nombreRol) {
        this.rolId = rolId;
        this.nombreRol = nombreRol;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}