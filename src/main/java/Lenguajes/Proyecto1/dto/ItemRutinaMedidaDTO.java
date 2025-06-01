package Lenguajes.Proyecto1.dto;

import jakarta.validation.constraints.*;

public class ItemRutinaMedidaDTO {

    @NotNull(message = "La medida corporal es obligatoria")
    private MedidaCorporalDTO medidaCorporal;

    private RutinaDTO rutina;

    @NotNull(message = "El valor de la medida es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor que 0")
    private Float valor;

    public MedidaCorporalDTO getMedidaCorporal() {
        return medidaCorporal;
    }
    public void setMedidaCorporal(MedidaCorporalDTO medidaCorporal) {
        this.medidaCorporal = medidaCorporal;
    }
    public RutinaDTO getRutina() {
        return rutina;
    }
    public void setRutina(RutinaDTO rutina) {
        this.rutina = rutina;
    }
    public Float getValor() {
        return valor;
    }
    public void setValor(Float valor) {
        this.valor = valor;
    }
}