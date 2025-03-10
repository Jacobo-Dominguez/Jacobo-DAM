package UML;

public class Computador {
    private String marca;
    private int cantidadMemoria;
    private String caracteristicasProcesador;
    private String sistemaOperativo;
    private double precio;

    public Computador(String marca, int cantidadMemoria, String caracteristicasProcesador, String sistemaOperativo, double precio) {
        this.marca = marca;
        this.cantidadMemoria = cantidadMemoria;
        this.caracteristicasProcesador = caracteristicasProcesador;
        this.sistemaOperativo = sistemaOperativo;
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCantidadMemoria() {
        return cantidadMemoria;
    }

    public void setCantidadMemoria(int cantidadMemoria) {
        this.cantidadMemoria = cantidadMemoria;
    }

    public String getCaracteristicasProcesador() {
        return caracteristicasProcesador;
    }

    public void setCaracteristicasProcesador(String caracteristicasProcesador) {
        this.caracteristicasProcesador = caracteristicasProcesador;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Computador{" +
                "marca='" + marca + '\'' +
                ", cantidadMemoria=" + cantidadMemoria +
                ", caracteristicasProcesador='" + caracteristicasProcesador + '\'' +
                ", sistemaOperativo='" + sistemaOperativo + '\'' +
                ", precio=" + precio +
                '}';
    }
}
