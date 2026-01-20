package org.example.ejercicio1;

public class Hilo extends Thread {

    private String texto;

    public Hilo(String texto) {
        this.texto = texto;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(texto);
        }
        try {
            int espera = (int) (Math.random() * 3000) + 1000;
            Thread.sleep(espera);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
