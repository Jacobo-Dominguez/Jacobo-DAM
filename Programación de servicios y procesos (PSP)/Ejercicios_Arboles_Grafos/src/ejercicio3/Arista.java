package ejercicio3;

public class Arista {
    Vertice origen, destino;
    double peso;
    String tipo;

    public Arista(Vertice origen, Vertice destino, double peso, String tipo) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.tipo = tipo;
    }
}
