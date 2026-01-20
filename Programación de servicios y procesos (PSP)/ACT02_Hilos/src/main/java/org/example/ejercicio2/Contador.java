package org.example.ejercicio2;

public class Contador extends Thread {

    private int inicio, fin;

    public Contador(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public void run() {
        for (int i = inicio; i <= fin; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}
