package Lenguajes.Proyecto1.domain;

public class ItemRutinaMedida {
    private MedidaCorporal medidaCorporal;
    private Rutina rutina;
    private float valor;

    public ItemRutinaMedida() {
        this.medidaCorporal = null;
        this.rutina = null;
        this.valor = 0.0f;
    }
    public ItemRutinaMedida(MedidaCorporal medidaCorporal, Rutina rutina, float valor) {
        this.medidaCorporal = medidaCorporal;
        this.rutina = rutina;
        this.valor = valor;
    }

    public MedidaCorporal getMedidaCorporal() { return this.medidaCorporal; }
    public void setMedidaCorporal(MedidaCorporal medidaCorporal) { this.medidaCorporal = medidaCorporal; }

    public Rutina getRutina() { return rutina; }
    public void setRutina(Rutina rutina) { this.rutina = rutina; }

    public float getValor() { return valor; }
    public void setValor(float valor) { this.valor = valor; }
}
