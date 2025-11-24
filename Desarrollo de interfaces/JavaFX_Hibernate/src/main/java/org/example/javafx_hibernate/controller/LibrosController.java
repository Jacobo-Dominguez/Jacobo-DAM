package org.example.javafx_hibernate.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.javafx_hibernate.dao.AutorDAO;
import org.example.javafx_hibernate.dao.AutorDAOImpl;
import org.example.javafx_hibernate.dao.LibroDAO;
import org.example.javafx_hibernate.dao.LibroDAOImpl;
import org.example.javafx_hibernate.entities.Autor;
import org.example.javafx_hibernate.entities.Libro;

import java.util.List;

public class LibrosController {

    // Inyección de componentes FXML
    @FXML
    private TextField txtTitulo;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtEditorial;
    @FXML private TextField txtAnio;
    @FXML private ComboBox<Autor> comboAutor;
    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colIsbn;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, Boolean> colDisponible;

    private final LibroDAO libroDAO = new LibroDAOImpl();
    private final AutorDAO autorDAO = new AutorDAOImpl();
    private final ObservableList<Libro> librosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colAutor.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getAutor().getNombre()
                )
        );
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        tablaLibros.setItems(librosData);

        // Cargar autores en combo
        cargarAutores();
        listarLibrosDisponibles();
    }

    private void cargarAutores() {
        List<Autor> autores = autorDAO.findAll();
        comboAutor.setItems(FXCollections.observableArrayList(autores));
    }

    @FXML
    private void onGuardarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String isbn = txtIsbn.getText().trim();
            String editorial = txtEditorial.getText().trim();
            String anioStr = txtAnio.getText().trim();
            Autor autor = comboAutor.getValue();

            if (titulo.isEmpty() || isbn.isEmpty() || autor == null) {
                showAlert("Error", "Título, ISBN y autor son obligatorios.");
                return;
            }

            Integer anio = anioStr.isEmpty() ? null : Integer.valueOf(anioStr);

            // Verificar ISBN único
            if (libroDAO.findByIsbn(isbn) != null) {
                showAlert("Error", "Ya existe un libro con ese ISBN.");
                return;
            }

            Libro libro = new Libro(titulo, isbn, editorial, anio, autor);
            libroDAO.create(libro);
            showAlert("Éxito", "Libro registrado correctamente.");
            limpiarFormulario();
            listarLibrosDisponibles();

        } catch (NumberFormatException e) {
            showAlert("Error", "El año debe ser un número válido.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo guardar el libro.");
        }
    }

    @FXML
    private void onEliminarLibro() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert("Advertencia", "Selecciona un libro para eliminar.");
            return;
        }

        if (libroDAO.deleteById(seleccionado.getId())) {
            showAlert("Éxito", "Libro eliminado.");
            listarLibrosDisponibles();
        } else {
            showAlert("Error", "No se pudo eliminar el libro.");
        }
    }

    @FXML
    private void onBuscarPorTitulo() {
        String titulo = pedirTexto("Buscar por título", "Ingresa parte del título:");
        if (titulo != null && !titulo.trim().isEmpty()) {
            List<Libro> libros = libroDAO.findByTitulo(titulo.trim());
            librosData.setAll(libros);
        }
    }

    @FXML
    private void onBuscarPorAutor() {
        String nombre = pedirTexto("Buscar por autor", "Ingresa parte del nombre del autor:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            List<Libro> libros = libroDAO.findByAutorNombre(nombre.trim());
            librosData.setAll(libros);
        }
    }

    @FXML
    private void onListarTodos() {
        listarLibrosDisponibles();
    }

    @FXML
    private void onNuevoAutor() {
        // Aquí podrías abrir una ventana modal (más adelante)
        showAlert("Info", "Función de crear autor desde aquí no implementada aún.");
    }

    private void listarLibrosDisponibles() {
        List<Libro> libros = libroDAO.findDisponibles();
        librosData.setAll(libros);
    }

    private void limpiarFormulario() {
        txtTitulo.clear();
        txtIsbn.clear();
        txtEditorial.clear();
        txtAnio.clear();
        comboAutor.setValue(null);
    }

    private String pedirTexto(String titulo, String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);
        return dialog.showAndWait().orElse(null);
    }

    private void showAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
