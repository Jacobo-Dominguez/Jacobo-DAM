package org.example.ejercicio6;

import javax.swing.*;
import java.awt.*;

public class TemporizadorSwing extends JFrame {

    private JProgressBar progressBar;
    private JLabel tiempo;
    private JTextField campoSegundos;
    private volatile boolean cancelado = false;

    public TemporizadorSwing() {
        setTitle("Temporizador");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        campoSegundos = new JTextField();
        progressBar = new JProgressBar(0, 100);
        tiempo = new JLabel("0 s", SwingConstants.CENTER);

        JButton iniciar = new JButton("Iniciar");
        JButton cancelar = new JButton("Cancelar");

        iniciar.addActionListener(e -> iniciar());
        cancelar.addActionListener(e -> cancelado = true);

        setLayout(new GridLayout(5, 1));
        add(campoSegundos);
        add(progressBar);
        add(tiempo);
        add(iniciar);
        add(cancelar);
    }

    private void iniciar() {
        cancelado = false;
        int max = Integer.parseInt(campoSegundos.getText());

        Thread hilo = new Thread(() -> {
            for (int i = 1; i <= max && !cancelado; i++) {
                int progreso = (int) ((i * 100.0) / max);
                int actual = i;

                SwingUtilities.invokeLater(() -> {
                    tiempo.setText(actual + " s");
                    progressBar.setValue(progreso);
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        });

        hilo.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TemporizadorSwing().setVisible(true);
        });
    }
}
