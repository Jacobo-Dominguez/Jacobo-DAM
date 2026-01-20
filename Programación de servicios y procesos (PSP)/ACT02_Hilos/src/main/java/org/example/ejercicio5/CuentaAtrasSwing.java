package org.example.ejercicio5;

import javax.swing.*;
import java.awt.*;

public class CuentaAtrasSwing extends JFrame {

    private JProgressBar progressBar;
    private JLabel mensaje;
    private JTextField campoSegundos;

    public CuentaAtrasSwing() {
        setTitle("Cuenta atrás");
        setSize(300, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        campoSegundos = new JTextField();
        progressBar = new JProgressBar(0, 100);
        mensaje = new JLabel("", SwingConstants.CENTER);

        JButton iniciar = new JButton("Iniciar");

        iniciar.addActionListener(e -> iniciarCuentaAtras());

        setLayout(new GridLayout(4, 1));
        add(campoSegundos);
        add(progressBar);
        add(mensaje);
        add(iniciar);
    }

    private void iniciarCuentaAtras() {
        int segundos = Integer.parseInt(campoSegundos.getText());

        Thread hilo = new Thread(() -> {
            for (int i = segundos; i >= 0; i--) {
                int progreso = (int) ((i * 100.0) / segundos);

                int finalI = i;
                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(progreso);
                    mensaje.setText("Tiempo: " + finalI + " s");
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }

            SwingUtilities.invokeLater(() ->
                    mensaje.setText("Tiempo finalizado")
            );
        });

        hilo.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CuentaAtrasSwing().setVisible(true);
        });
    }
}
