package ejercicio3;

import java.util.*;

public class GrafoRutas {
    private Map<String, Vertice> vertices = new HashMap<>();

    public Vertice agregarVertice(String nombre) {
        Vertice v = new Vertice(nombre);
        vertices.put(nombre, v);
        return v;
    }

    public void conectar(String origen, String destino, double peso, String tipo) {
        Vertice v1 = vertices.get(origen);
        Vertice v2 = vertices.get(destino);
        if (v1 != null && v2 != null)
            v1.agregarArista(v2, peso, tipo);
    }

    public void dijkstra(String inicio, String fin, Set<String> evitarTipos) {
        Map<Vertice, Double> dist = new HashMap<>();
        Map<Vertice, Vertice> prev = new HashMap<>();
        PriorityQueue<Vertice> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (Vertice v : vertices.values()) dist.put(v, Double.POSITIVE_INFINITY);
        Vertice origen = vertices.get(inicio);
        dist.put(origen, 0.0);
        pq.add(origen);

        while (!pq.isEmpty()) {
            Vertice actual = pq.poll();
            if (actual.nombre.equals(fin)) break;

            for (Arista arista : actual.adyacentes) {
                if (evitarTipos.contains(arista.tipo)) continue;
                double nuevaDist = dist.get(actual) + arista.peso;
                if (nuevaDist < dist.get(arista.destino)) {
                    dist.put(arista.destino, nuevaDist);
                    prev.put(arista.destino, actual);
                    pq.add(arista.destino);
                }
            }
        }

        mostrarRuta(fin, prev, dist);
    }

    private void mostrarRuta(String fin, Map<Vertice, Vertice> prev, Map<Vertice, Double> dist) {
        Vertice destino = vertices.get(fin);
        if (dist.get(destino) == Double.POSITIVE_INFINITY) {
            System.out.println("No hay ruta disponible.");
            return;
        }

        List<String> camino = new ArrayList<>();
        for (Vertice v = destino; v != null; v = prev.get(v))
            camino.add(v.nombre);
        Collections.reverse(camino);

        System.out.println("Ruta óptima: " + String.join(" -> ", camino));
        System.out.println("Tiempo total: " + dist.get(destino));
    }
}
