package org.example.ejercicio4;

import java.util.Scanner;

public class CarreraMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Distancia del circuito (metros): ");
        int distancia = sc.nextInt();

        System.out.print("Número de coches (1 a 4): ");
        int numCoches = sc.nextInt();

        if (numCoches < 1 || numCoches > 4) {
            System.out.println("Número de coches no válido");
            return;
        }

        Coche[] coches = new Coche[numCoches];

        for (int i = 0; i < numCoches; i++) {
            System.out.print("Velocidad del coche " + (i + 1) + ": ");
            int velocidad = sc.nextInt();
            coches[i] = new Coche("Coche-" + (i + 1), velocidad, distancia);
        }

        System.out.println("\n🚦 ¡Comienza la carrera!\n");

        for (Coche c : coches) {
            c.start();
        }
    }
}
