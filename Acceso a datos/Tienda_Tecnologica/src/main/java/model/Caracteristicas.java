package model;

public class Caracteristicas {
    private String pantalla;
    private String camara;
    private String bateria;

    // Getters y setters
    public String getPantalla() { return pantalla; }
    public void setPantalla(String pantalla) { this.pantalla = pantalla; }

    public String getCamara() { return camara; }
    public void setCamara(String camara) { this.camara = camara; }

    public String getBateria() { return bateria; }
    public void setBateria(String bateria) { this.bateria = bateria; }

    @Override
    public String toString() {
        return "Pantalla: " + pantalla + ", Camara: " + camara + ", Bateria: " + bateria;
    }
}
