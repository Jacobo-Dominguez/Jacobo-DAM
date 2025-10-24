package model;

public class Direccion {
    private String calle;
    private int numero;
    private String ciudad;
    private String pais;

    // Getters y setters
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        return calle + " " + numero + ", " + ciudad + " (" + pais + ")";
    }
}
