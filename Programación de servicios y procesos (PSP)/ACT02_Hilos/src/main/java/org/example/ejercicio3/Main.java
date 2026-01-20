package org.example.ejercicio3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el número máximo: ");
        int max = sc.nextInt();

        System.out.print("Introduce el número de hilos: ");
        int numHilos = sc.nextInt();

        int rango = max / numHilos;
        int inicio = 1;

        for (int i = 0; i < numHilos; i++) {
            int fin = (i == numHilos - 1) ? max : inicio + rango - 1;

            Thread hilo = new Thread(
                    new Contador(inicio, fin),
                    "Hilo-" + (i + 1)
            );

            hilo.start();
            inicio = fin + 1;
        }
    }
}

