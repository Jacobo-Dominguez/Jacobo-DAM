package ejercicio3;

import java.util.*;

public class Vertice {
    String nombre;
    List<Arista> adyacentes;

    public Vertice(String nombre) {
        this.nombre = nombre;
        this.adyacentes = new ArrayList<>();
    }

    public void agregarArista(Vertice destino, double peso, String tipo) {
        adyacentes.add(new Arista(this, destino, peso, tipo));
    }
}
