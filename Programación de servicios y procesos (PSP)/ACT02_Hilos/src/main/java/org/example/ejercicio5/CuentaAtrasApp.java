package org.example.ejercicio5;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CuentaAtrasApp extends Application {

    @Override
    public void start(Stage stage) {

        TextField campoSegundos = new TextField();
        campoSegundos.setPromptText("Segundos");

        ProgressBar barra = new ProgressBar(1);
        Label mensaje = new Label();

        Button iniciar = new Button("Iniciar");

        iniciar.setOnAction(e -> {
            int segundos = Integer.parseInt(campoSegundos.getText());

            Thread hilo = new Thread(() -> {
                for (int i = segundos; i >= 0; i--) {
                    double progreso = (double) i / segundos;

                    Platform.runLater(() -> barra.setProgress(progreso));

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        return;
                    }
                }

                Platform.runLater(() -> mensaje.setText("Tiempo finalizado"));
            });

            hilo.start();
        });

        VBox root = new VBox(10, campoSegundos, barra, mensaje, iniciar);
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Cuenta atrás");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
