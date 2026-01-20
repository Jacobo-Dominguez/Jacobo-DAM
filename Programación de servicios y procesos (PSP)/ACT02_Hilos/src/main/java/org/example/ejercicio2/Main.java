package org.example.ejercicio2;


public class Main {
    public static void main(String[] args) {

        Thread h1 = new Thread(new Contador(1, 50), "Hilo 1");
        Thread h2 = new Thread(new Contador(51, 100), "Hilo 2");

        h1.start();
        h2.start();
    }
}
