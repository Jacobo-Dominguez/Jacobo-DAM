package org.example.javafx_hibernate.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.javafx_hibernate.dao.SocioDAO;
import org.example.javafx_hibernate.dao.SocioDAOImpl;
import org.example.javafx_hibernate.entities.Socio;

import java.util.List;

public class SociosController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TableView<Socio> tablaSocios;
    @FXML private TableColumn<Socio, String> colNombre;
    @FXML private TableColumn<Socio, String> colDireccion;
    @FXML private TableColumn<Socio, String> colTelefono;

    private final SocioDAO socioDAO = new SocioDAOImpl();
    private final ObservableList<Socio> sociosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tablaSocios.setItems(sociosData);
        listarTodos();
    }

    @FXML
    private void onGuardarSocio() {
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        if (nombre.isEmpty() || telefono.isEmpty()) {
            showAlert("Error", "Nombre y teléfono son obligatorios.");
            return;
        }
        Socio socio = new Socio(nombre, direccion, telefono);
        socioDAO.create(socio);
        showAlert("Éxito", "Socio registrado.");
        limpiarFormulario();
        listarTodos();
    }

    @FXML
    private void onEliminarSocio() {
        Socio seleccionado = tablaSocios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert("Advertencia", "Selecciona un socio.");
            return;
        }
        if (socioDAO.deleteById(seleccionado.getId())) {
            showAlert("Éxito", "Socio eliminado.");
            listarTodos();
        } else {
            showAlert("Error", "No se pudo eliminar el socio.");
        }
    }

    @FXML
    private void onBuscarPorNombre() {
        String nombre = pedirTexto("Buscar socio", "Nombre (o parte):");
        if (nombre != null && !nombre.trim().isEmpty()) {
            List<Socio> socios = socioDAO.findByNombre(nombre.trim());
            sociosData.setAll(socios);
        }
    }

    @FXML
    private void onBuscarPorTelefono() {
        String telefono = pedirTexto("Buscar por teléfono", "Teléfono:");
        if (telefono != null && !telefono.trim().isEmpty()) {
            List<Socio> socios = socioDAO.findByTelefono(telefono.trim());
            sociosData.setAll(socios);
        }
    }

    @FXML
    private void onListarTodos() {
        listarTodos();
    }

    private void listarTodos() {
        List<Socio> socios = socioDAO.findAll();
        sociosData.setAll(socios);
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtDireccion.clear();
        txtTelefono.clear();
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
