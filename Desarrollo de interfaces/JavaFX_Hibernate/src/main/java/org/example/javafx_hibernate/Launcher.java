package org.example.javafx_hibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.example.javafx_hibernate.Util.HibernateUtil;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) {
        // Verificar conexión
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Conexión a base de datos exitosa.");
        } catch (Exception e) {
            System.err.println("Error de conexión.");
            e.printStackTrace();
            return;
        }

        // Crear pestañas que cargan vistas FXML
        TabPane tabPane = new TabPane();

        Tab tabLibros = new Tab("Libros");
        tabLibros.setContent(loadView("/org/example/javafx_hibernate/view/libros-view.fxml"));

        Tab tabAutores = new Tab("Autores");
        tabAutores.setContent(loadView("/org/example/javafx_hibernate/view/autores-view.fxml"));

        Tab tabSocios = new Tab("Socios");
        tabSocios.setContent(loadView("/org/example/javafx_hibernate/view/socios-view.fxml"));

        Tab tabPrestamos = new Tab("Préstamos");
        tabPrestamos.setContent(loadView("/org/example/javafx_hibernate/view/prestamos-view.fxml"));

        tabPane.getTabs().addAll(tabLibros, tabAutores, tabSocios, tabPrestamos);
        //tabPane.getTabs().addAll(tabLibros);

        Scene scene = new Scene(tabPane, 950, 650);
        stage.setTitle("Biblioteca - Gestión");
        stage.setScene(scene);
        stage.show();
    }

    private javafx.scene.Parent loadView(String path) {
        try {
            return FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar la vista: " + path);
        }
    }

    @Override
    public void stop() {
        HibernateUtil.getSessionFactory().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
