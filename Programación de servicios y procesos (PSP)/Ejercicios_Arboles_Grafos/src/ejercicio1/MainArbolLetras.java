package ejercicio1;

import java.util.Scanner;

public class MainArbolLetras {
    public static void main(String[] args) {
        ArbolLetras arbol = new ArbolLetras();
        Scanner sc = new Scanner(System.in);

        // Construir un árbol base
        for (char c : new char[]{'M', 'C', 'R', 'A', 'E', 'P', 'T'}) {
            arbol.insertar(c);
        }

        System.out.println("Árbol por niveles:");
        arbol.mostrarPorNiveles();

        System.out.print("\nIngrese una letra para buscar: ");
        String entrada = sc.nextLine().trim().toUpperCase();

        if (entrada.isEmpty()) {
            System.out.println("Entrada vacía, intente nuevamente.");
            return;
        }

        char letra = entrada.charAt(0);
        arbol.buscarCamino(letra);
    }
}
