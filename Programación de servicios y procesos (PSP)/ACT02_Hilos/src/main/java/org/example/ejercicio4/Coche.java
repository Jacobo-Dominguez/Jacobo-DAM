package org.example.ejercicio4;

public class Coche extends Thread {

    private String nombre;
    private int velocidad;        // metros por ciclo
    private int distanciaTotal;
    private int recorrido = 0;

    private static volatile boolean carreraTerminada = false;

    public Coche(String nombre, int velocidad, int distanciaTotal) {
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.distanciaTotal = distanciaTotal;
    }

    @Override
    public void run() {
        while (recorrido < distanciaTotal && !carreraTerminada) {
            recorrido += velocidad;
            if (recorrido > distanciaTotal) {
                recorrido = distanciaTotal;
            }

            mostrarProgreso();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                return;
            }
        }

        if (!carreraTerminada) {
            carreraTerminada = true;
            System.out.println("\n🏆 GANADOR: " + nombre);
        } else {
            System.out.println(nombre + " se detuvo en " + recorrido + " metros");
        }
    }

    private synchronized void mostrarProgreso() {
        int porcentaje = (recorrido * 100) / distanciaTotal;
        int barras = porcentaje / 5; // 20 bloques

        String barra = "#".repeat(barras) + "-".repeat(20 - barras);
        System.out.printf("%-10s [%s] %3d%%%n", nombre, barra, porcentaje);
    }
}
