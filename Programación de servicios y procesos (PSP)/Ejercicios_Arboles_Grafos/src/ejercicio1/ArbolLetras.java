package ejercicio1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolLetras {
    private NodoLetra raiz;

    public ArbolLetras() {
        this.raiz = null;
    }

    public void insertar(char letra) {
        raiz = insertarRec(raiz, letra);
    }

    private NodoLetra insertarRec(NodoLetra actual, char letra) {
        if (actual == null) return new NodoLetra(letra);
        if (letra < actual.letra)
            actual.izquierda = insertarRec(actual.izquierda, letra);
        else if (letra > actual.letra)
            actual.derecha = insertarRec(actual.derecha, letra);
        return actual;
    }

    public void mostrarPorNiveles() {
        if (raiz == null) return;
        Queue<NodoLetra> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            int nivel = cola.size();
            for (int i = 0; i < nivel; i++) {
                NodoLetra nodo = cola.poll();
                System.out.print(nodo.letra + " ");
                if (nodo.izquierda != null) cola.add(nodo.izquierda);
                if (nodo.derecha != null) cola.add(nodo.derecha);
            }
            System.out.println();
        }
    }

    public boolean buscarCamino(char objetivo) {
        List<Character> letras = new ArrayList<>();
        List<Character> direcciones = new ArrayList<>();
        boolean encontrado = buscarDFS(raiz, objetivo, letras, direcciones);
        if (encontrado) {
            System.out.println("Camino de letras: " + letras);
            System.out.println("Direcciones (L=izq, R=der): " + direcciones);
        } else {
            System.out.println("La letra '" + objetivo + "' no se encuentra en el árbol.");
        }
        return encontrado;
    }

    private boolean buscarDFS(NodoLetra nodo, char objetivo, List<Character> letras, List<Character> direcciones) {
        if (nodo == null) return false;
        letras.add(nodo.letra);

        if (nodo.letra == objetivo) return true;

        direcciones.add('L');
        if (buscarDFS(nodo.izquierda, objetivo, letras, direcciones)) return true;
        direcciones.remove(direcciones.size() - 1);

        direcciones.add('R');
        if (buscarDFS(nodo.derecha, objetivo, letras, direcciones)) return true;
        direcciones.remove(direcciones.size() - 1);

        letras.remove(letras.size() - 1);
        return false;
    }
}