package org.example.ejercicio6;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TemporizadorApp extends Application {

    private volatile boolean cancelado = false;

    @Override
    public void start(Stage stage) {

        TextField campoSegundos = new TextField();
        campoSegundos.setPromptText("Segundos");

        ProgressBar barra = new ProgressBar(0);
        Label tiempo = new Label("0 s");

        Button iniciar = new Button("Iniciar");
        Button cancelar = new Button("Cancelar");

        iniciar.setOnAction(e -> {
            cancelado = false;
            int max = Integer.parseInt(campoSegundos.getText());

            Thread hilo = new Thread(() -> {
                for (int i = 1; i <= max && !cancelado; i++) {

                    int actual = i;
                    Platform.runLater(() -> {
                        tiempo.setText(actual + " s");
                        barra.setProgress((double) actual / max);
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
            });

            hilo.start();
        });

        cancelar.setOnAction(e -> cancelado = true);

        VBox root = new VBox(10, campoSegundos, barra, tiempo, iniciar, cancelar);
        stage.setScene(new Scene(root, 300, 220));
        stage.setTitle("Temporizador");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
