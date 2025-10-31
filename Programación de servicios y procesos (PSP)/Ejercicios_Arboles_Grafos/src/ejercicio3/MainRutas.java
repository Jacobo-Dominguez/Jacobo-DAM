package ejercicio3;

import java.util.*;

public class MainRutas {
    public static void main(String[] args) {
        GrafoRutas grafo = new GrafoRutas();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        grafo.conectar("A", "B", 5, "peatonal");
        grafo.conectar("A", "C", 2, "bicicleta");
        grafo.conectar("C", "D", 3, "metro");
        grafo.conectar("B", "D", 4, "bus");

        System.out.println("Ruta más rápida de A a D evitando bicicleta:");
        grafo.dijkstra("A", "D", Set.of("bicicleta"));
    }
}