package org.example.ejercicio1;

public class Main {
    public static void main(String[] args) {

        Hilo h1 = new Hilo("Hola desde el hilo 1");
        Hilo h2 = new Hilo("Hola desde el hilo 2");

        h1.start();
        h2.start();

    }
}
