package org.example.calculadorafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("calculadora-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        scene.getStylesheets().add(App.class.getResource("calculadora.css").toExternalForm());
        stage.setTitle("CalculadoraFX");
        stage.setScene(scene);
        stage.show();
    }
}
