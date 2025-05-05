package Lenguajes.Proyecto1.dto;

import java.time.LocalDate;

public class ClienteDTO {

    private int idCliente;
    private String numeroIdentificacion;
    private String nombreCliente;
    private String apellidosCliente;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
    private String nombreContactoEmergencia;
    private String telefonoContactoEmergencia;
    private String fotografia;

    // Constructor
    public ClienteDTO(int idCliente, String numeroIdentificacion, String nombreCliente, String apellidosCliente,
                      LocalDate fechaNacimiento, String telefono, String direccion,
                      String nombreContactoEmergencia, String telefonoContactoEmergencia, String fotografia) {
        this.idCliente = idCliente;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreCliente = nombreCliente;
        this.apellidosCliente = apellidosCliente;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombreContactoEmergencia = nombreContactoEmergencia;
        this.telefonoContactoEmergencia = telefonoContactoEmergencia;
        this.fotografia = fotografia;
    }

    
    public ClienteDTO(String numeroIdentificacion, String nombreCliente, String apellidosCliente,
            LocalDate fechaNacimiento, String telefono, String direccion,
            String nombreContactoEmergencia, String telefonoContactoEmergencia, String fotografia) {

this.numeroIdentificacion = numeroIdentificacion;
this.nombreCliente = nombreCliente;
this.apellidosCliente = apellidosCliente;
this.fechaNacimiento = fechaNacimiento;
this.telefono = telefono;
this.direccion = direccion;
this.nombreContactoEmergencia = nombreContactoEmergencia;
this.telefonoContactoEmergencia = telefonoContactoEmergencia;
this.fotografia = fotografia;
}
    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreContactoEmergencia() {
        return nombreContactoEmergencia;
    }

    public void setNombreContactoEmergencia(String nombreContactoEmergencia) {
        this.nombreContactoEmergencia = nombreContactoEmergencia;
    }

    public String getTelefonoContactoEmergencia() {
        return telefonoContactoEmergencia;
    }

    public void setTelefonoContactoEmergencia(String telefonoContactoEmergencia) {
        this.telefonoContactoEmergencia = telefonoContactoEmergencia;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }
}