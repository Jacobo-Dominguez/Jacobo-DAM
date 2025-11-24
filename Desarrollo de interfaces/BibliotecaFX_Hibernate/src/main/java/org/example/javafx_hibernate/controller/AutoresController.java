package org.example.javafx_hibernate.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.javafx_hibernate.dao.AutorDAO;
import org.example.javafx_hibernate.dao.AutorDAOImpl;
import org.example.javafx_hibernate.entities.Autor;

import java.util.List;

public class AutoresController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtNacionalidad;
    @FXML private TableView<Autor> tablaAutores;
    @FXML private TableColumn<Autor, String> colNombre;
    @FXML
    private TableColumn<Autor, String> colNacionalidad;

    private final AutorDAO autorDAO = new AutorDAOImpl();
    private final ObservableList<Autor> autoresData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        tablaAutores.setItems(autoresData);
        listarTodos();
    }

    @FXML
    private void onGuardarAutor() {
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();
        if (nombre.isEmpty()) {
            showAlert("Error", "El nombre es obligatorio.");
            return;
        }
        Autor autor = new Autor(nombre, nacionalidad);
        autorDAO.create(autor);
        showAlert("Éxito", "Autor registrado.");
        limpiarFormulario();
        listarTodos();
    }

    @FXML
    private void onEliminarAutor() {
        Autor seleccionado = tablaAutores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert("Advertencia", "Selecciona un autor.");
            return;
        }
        if (autorDAO.deleteById(seleccionado.getId())) {
            showAlert("Éxito", "Autor eliminado.");
            listarTodos();
        } else {
            showAlert("Error", "No se pudo eliminar el autor.");
        }
    }

    @FXML
    private void onBuscarPorNombre() {
        String nombre = pedirTexto("Buscar autor", "Nombre (o parte del nombre):");
        if (nombre != null && !nombre.trim().isEmpty()) {
            List<Autor> autores = autorDAO.findByNombre(nombre.trim());
            autoresData.setAll(autores);
        }
    }

    @FXML
    private void onListarTodos() {
        listarTodos();
    }

    private void listarTodos() {
        List<Autor> autores = autorDAO.findAll();
        autoresData.setAll(autores);
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtNacionalidad.clear();
    }

    private String pedirTexto(String titulo, String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);
        return dialog.showAndWait().orElse(null);
    }

    private void showAlert(String titulo, String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}
